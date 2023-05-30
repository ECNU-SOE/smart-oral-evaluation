package net.ecnu.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.constant.RolesConst;
import net.ecnu.controller.request.SignReq;
import net.ecnu.controller.request.UserFilterReq;
import net.ecnu.controller.request.UserReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.UserManager;
import net.ecnu.mapper.*;
import net.ecnu.model.SignDO;
import net.ecnu.model.SignLogDO;
import net.ecnu.model.UserDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.SignVO;
import net.ecnu.model.vo.UserVO;
import net.ecnu.service.UserService;
import net.ecnu.util.FileUtil;
import net.ecnu.util.IDUtil;
import net.ecnu.util.JWTUtil;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserManager userManager;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private MyUserDetailsServiceMapper mapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SignMapper signMapper;

    @Resource
    private SignLogMapper signLogMapper;

    @Override
    public Object register(UserReq userReq) {
        //check手机验证码 与 数据库唯一性
        UserDO userDO = userManager.selectOneByPhone(userReq.getPhone());
        if (userDO != null) throw new BizException(BizCodeEnum.ACCOUNT_REPEAT);

        //处理生成userDO对象，插入数据库
        UserDO newUserDO = new UserDO();
        newUserDO.setAccountNo(IDUtil.nextUserId());
        newUserDO.setNickName(userReq.getNickName());
        String defaultAvatarUrl = "https://img2.woyaogexing.com/2023/05/14/11d01024a840b3f4c3a4e1eda17deb55.png";
        newUserDO.setAvatarUrl(defaultAvatarUrl);
        newUserDO.setPhone(userReq.getPhone());
//        //密码加密处理
//        newUserDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8)); //加密盐
//        newUserDO.setPwd(Md5Crypt.md5Crypt(userRegisterReq.getPwd().getBytes(), newUserDO.getSecret()));
        newUserDO.setPwd(passwordEncoder.encode(userReq.getPwd()));
        int rows = mapper.insertUserRole(RolesConst.DEFAULT_ROLE, newUserDO.getAccountNo());
        rows += userMapper.insert(newUserDO);
        return "register success";
    }

    @Override
    public Object login(UserReq userReq) {
        LoginUser loginUser = new LoginUser();
        try {
            UsernamePasswordAuthenticationToken upToken =
                    new UsernamePasswordAuthenticationToken(userReq.getPhone(), userReq.getPwd());
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDO userInfoByPhone = userManager.selectOneByPhone(userReq.getPhone());
            BeanUtils.copyProperties(userInfoByPhone, loginUser);
        } catch (AuthenticationException e) {
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(), BizCodeEnum.USER_INPUT_ERROR.getMessage());
        }
        //验证成功，生成token并返回
        return JWTUtil.geneJsonWebToken(loginUser);
    }

    @Override
    public Object getUserInfo() {
//        LoginUser loginUser = LoginInterceptor.threadLocal.get();
//        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //如果token有效，数据库查询用户个人信息，TODO del待判断
        UserDO userDO = userMapper.selectById(currentAccountNo);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    @Override
    public Object pageByFilter(UserFilterReq userFilterReq, PageData pageData) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        if (!checkPermission(currentAccountNo)) {
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
        List<UserDO> userDOS = userManager.pageByFilter(userFilterReq, pageData);
        int total = userManager.countByFilter(userFilterReq);
        pageData.setTotal(total);
        List<UserVO> userVOS = userDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        pageData.setRecords(userVOS);
        return pageData;
    }

    @Override
    public Object update(UserReq userReq) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        if (userReq.getAccountNo() == null || StringUtils.isBlank(userReq.getAccountNo()) || Objects.equals(currentAccountNo, userReq.getAccountNo())) {
            //用户更新自己的信息
            UserDO userDO = userMapper.selectById(currentAccountNo);
            BeanUtils.copyProperties(userReq, userDO, "accountNo", "phone", "pwd");
            userDO.setGmtModified(null);//更新时间
            int i = userMapper.updateById(userDO);
            return userMapper.selectById(currentAccountNo);
        } else {
            //用户更新别人的信息
            Integer roleA = getTopRole(currentAccountNo);
            Integer roleB = getTopRole(userReq.getAccountNo());
            if (!hasOpRight(roleA, roleB))
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            UserDO userDO = new UserDO();
            BeanUtils.copyProperties(userReq, userDO, "phone", "pwd");
            return userMapper.updateById(userDO);
        }
    }

    @Override
    public Object batch(MultipartFile excelFile) throws IOException {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        Sheet sheet = getFirstSheet(excelFile);
        List<UserDO> data = getData(sheet);
        int count = 0;
        for (int i = 1; i < data.size(); i++) {
            UserDO newUserDo = data.get(i);
            UserDO userDO = userManager.selectOneByPhone(newUserDo.getPhone());
            if (userDO != null)
                throw new BizException(BizCodeEnum.ACCOUNT_REPEAT);
            userMapper.insert(newUserDo);
            mapper.insertUserRole(RolesConst.DEFAULT_ROLE, newUserDo.getAccountNo());
            count++;
        }
        return count;
    }

    @Override
    public boolean send(String phoneNum) {
        UserDO userDO = userManager.selectOneByPhone(phoneNum);
        if (userDO == null)
            return false;
        final String templateCode = "SMS_262610596"; //阿里云短信模板,需要向阿里云申请
        final String signName = "唐国兴的博客"; //短信签名，需要向阿里云申请

        //连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tKC7ayyoZA81SE62bpH", "0v3aCE7DZyXXueE4Ui2BC8fwIVEOMj");
        IAcsClient client = new DefaultAcsClient(profile);
        //构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");//不要动
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        //自定义请求参数
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", signName);    //短信签名
        request.putQueryParameter("TemplateCode", templateCode);//短信模板code
        String code = RandomUtil.randomNumbers(6);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));
        try {
            CommonResponse resp = client.getCommonResponse(request);// 发送短信
            String s = new String(resp.getData().getBytes(), StandardCharsets.UTF_8);
            System.out.println(s);
            return resp.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

    private UserVO beanProcess(UserDO userDO) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    private Boolean checkPermission(String accountNo) {
        Integer topRole = getTopRole(accountNo);
        if (topRole > RolesConst.ROLE_ADMIN) {
            return false;
        }
        return true;
    }

    @Override
    public Integer getTopRole(String accountNo) {
        List<String> roles_temp = userRoleMapper.getRoles(accountNo);
        if (roles_temp.size() == 0)
            return 8;//用户没有设置权限id，则默认返回8：游客
        List<Integer> roles = roles_temp.stream().map(Integer::parseInt).collect(Collectors.toList());
        return Collections.min(roles);
    }

    @Override
    public Object del(String accountNo) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        UserDO userDO = userMapper.selectById(accountNo);
        if (userDO == null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if (!hasDelRight(currentAccountNo, accountNo))
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        UserDO newUserDo = new UserDO();
        BeanUtils.copyProperties(userDO, newUserDo, "del");
        newUserDo.setDel(true);
        return userMapper.updateById(newUserDo);
    }

    @Override
    public Object sign() {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //获取今天日期
        LocalDate today  = LocalDate.now();
        //判断用户是否首次签到
        SignDO signDO = signMapper.selectOne(new QueryWrapper<SignDO>()
                .eq("user_id", currentAccountNo)
        );
        if (signDO==null){
            //首次签到，先创建签到信息
            SignDO newSignDO = new SignDO();
            newSignDO.setUserId(currentAccountNo);
            newSignDO.setTotalDays(1);
            newSignDO.setContinueDays(1);
            newSignDO.setLastSign(today);
            newSignDO.setResignNum(10);//补签次数，默认为10次
            int countSign = signMapper.insert(newSignDO);
            //再插入签到记录
            SignLogDO newSignLogDO = new SignLogDO();
            newSignLogDO.setUserId(currentAccountNo);
            newSignLogDO.setSignDate(today);
            newSignLogDO.setSignType(1);//1：签到，2：补签
            int countSignLog = signLogMapper.insert(newSignLogDO);
            return "签到成功";
        }else {
            //非首次签到,判断用户今天是否已签到
            LocalDate lastSignDay = signDO.getLastSign();
            if (lastSignDay.compareTo(today)==0)
                throw new BizException(BizCodeEnum.USER_SIGNED);
            //用户签到，先创建signLog，再更新sign表
            SignLogDO newSignLogDO = new SignLogDO();
            newSignLogDO.setUserId(currentAccountNo);
            newSignLogDO.setSignDate(today);
            newSignLogDO.setSignType(1);
            int countSignLog = signLogMapper.insert(newSignLogDO);
            SignDO newSignDO = new SignDO();
            BeanUtils.copyProperties(signDO,newSignDO,"total_days","continue_days","last_sign");
            newSignDO.setLastSign(today);
            newSignDO.setTotalDays(signDO.getTotalDays()+1);
            if (lastSignDay.until(today, ChronoUnit.DAYS)==1)
                newSignDO.setContinueDays(signDO.getContinueDays()+1);
            else
                newSignDO.setContinueDays(1);//重置连续签到天数
            int countSign = signMapper.updateById(newSignDO);
            return "签到成功";
        }
    }

    @Override
    public Object resign(LocalDate resignDate) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //获取今天日期
        LocalDate today = LocalDate.now();
        //判断用户是否已有签到记录
        SignDO signDO = signMapper.selectOne(new QueryWrapper<SignDO>()
                .eq("user_id", currentAccountNo)
        );
        if (signDO==null)
            return "用户暂无签到记录";
        else{
            //用户已有签到记录，校验补签日期是否正常
            //1.补签日期应早于今天
            if (resignDate.until(today,ChronoUnit.DAYS)<=0)
                return "补签日期异常";
            //2.补签日期未签到
            List<LocalDate> signDates = signLogMapper.getSignDatesDescByAccountNo(currentAccountNo);
            if (signDates.contains(resignDate))
                throw new BizException(BizCodeEnum.USER_SIGNED);
            //无异常，用户补签，先增加signLog表记录
            SignLogDO newSignLogDO = new SignLogDO();
            newSignLogDO.setUserId(currentAccountNo);
            newSignLogDO.setSignDate(resignDate);
            newSignLogDO.setSignType(2);
            int countSignLog = signLogMapper.insert(newSignLogDO);
            //再更新sign表
            SignDO newSignDO = new SignDO();
            BeanUtils.copyProperties(signDO,newSignDO,"total_days","continue_days","last_sign");
            newSignDO.setTotalDays(signDO.getTotalDays()+1);
            if (resignDate.until(signDO.getLastSign(),ChronoUnit.DAYS)<0)
                newSignDO.setLastSign(resignDate);
            else
                newSignDO.setLastSign(signDO.getLastSign());
            int continueDays = 0;
            LocalDate initaialDay;
            if (signDates.contains(today)){
                initaialDay = today;
            } else {
                initaialDay = today.plusDays(-1);
            }
            List<LocalDate> newSignDates = signLogMapper.getSignDatesDescByAccountNo(currentAccountNo);
            //统计补签后的连续签到时间
            for (LocalDate date = initaialDay;;date=date.plusDays(-1)){
                if (newSignDates.contains(date))
                    continueDays++;
                else
                    break;
            }
            newSignDO.setContinueDays(continueDays);
            int countSign = signMapper.updateById(newSignDO);
            return "补签成功";
        }
    }

    @Override
    public Object signInfo() {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //判断用户是否已有签到记录
        SignDO signDO = signMapper.selectOne(new QueryWrapper<SignDO>()
                .eq("user_id", currentAccountNo)
        );
        if (signDO==null)
            return "用户暂无签到记录";
        else {
            SignVO signVO = new SignVO();
            BeanUtils.copyProperties(signDO,signVO);
            return signVO;
        }
    }

    @Override
    public Object getInfoList(List<String> accountList) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        List<UserDO> userDOS = userMapper.selectBatchIds(accountList);
        List<UserVO> userVOS = userDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        return userVOS;
    }

    private boolean hasOpRight(Integer roleA, Integer roleB) {
        //角色a为管理员，且b不是超管
        if (roleA <= RolesConst.ROLE_ADMIN && !Objects.equals(roleB, RolesConst.ROLE_SUPER_ADMIN))
            return true;
        //角色a权限高于角色b
        if (roleA < roleB)
            return true;
        return false;
    }

    private Sheet getFirstSheet(MultipartFile excelFile) throws IOException {
        File file = FileUtil.transferToFile(excelFile);
        InputStream inputStream = Files.newInputStream(file.toPath());
        Workbook workbook = null;
        String name = excelFile.getOriginalFilename();
        assert name != null;
        if ("xls".equals(name.substring(name.lastIndexOf(".") + 1))) {
            workbook = new HSSFWorkbook(inputStream);
        } else if ("xlsx".equals(name.substring(name.lastIndexOf(".") + 1))) {
            workbook = new XSSFWorkbook(inputStream);
        }
        assert workbook != null;
        return workbook.getSheetAt(0);
    }

    private List<UserDO> getData(Sheet sheet) {
        List<UserDO> data = new ArrayList<>();
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            UserDO userDO = new UserDO();
            for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
                Cell cell = row.getCell(index);
                cell.setCellType(CellType.STRING);
                switch (index) {
                    case 0:
                        if (!StringUtils.isBlank(cell.getStringCellValue())) {
                            userDO.setRealName(cell.getStringCellValue());
                        }
                    case 1:
                        if (!StringUtils.isBlank(cell.getStringCellValue())) {
                            userDO.setPhone(cell.getStringCellValue());
                        }

                    case 2:
                        if (!StringUtils.isBlank(cell.getStringCellValue())) {
                            userDO.setIdentifyId(cell.getStringCellValue());
                        }
                }
            }
            userDO.setPwd(passwordEncoder.encode("soe12345"));
            userDO.setAccountNo("user_" + IDUtil.getSnowflakeId());
            data.add(userDO);
        }
        return data;
    }

    //当前用户有无删除此用户的权限
    private Boolean hasDelRight(String currentAccountNo, String accountNo) {
        Integer roleA = getTopRole(currentAccountNo);
        Integer roleB = getTopRole(accountNo);
        if (roleA <= RolesConst.ROLE_ADMIN && !Objects.equals(roleB, RolesConst.ROLE_SUPER_ADMIN))
            return true;
        if (Objects.equals(currentAccountNo, accountNo))
            return true;
        return false;
    }

}










