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
import lombok.extern.slf4j.Slf4j;
import net.ecnu.constant.RolesConst;
import net.ecnu.controller.request.UserFilterReq;
import net.ecnu.controller.request.UserReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.UserManager;
import net.ecnu.mapper.MyUserDetailsServiceMapper;
import net.ecnu.mapper.UserMapper;
import net.ecnu.mapper.UserRoleMapper;
import net.ecnu.model.ClassDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.UserDO;
import net.ecnu.model.common.PageData;
import net.ecnu.model.dto.UserDTO;
import net.ecnu.model.vo.ClassVO;
import net.ecnu.model.vo.UserVO;
import net.ecnu.service.UserService;
import net.ecnu.util.IDUtil;
import net.ecnu.util.JWTUtil;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public Object register(UserReq userReq) {
        //check手机验证码 与 数据库唯一性
        UserDO userDO = userManager.selectOneByPhone(userReq.getPhone());
        if (userDO != null) throw new BizException(BizCodeEnum.ACCOUNT_REPEAT);

        //处理生成userDO对象，插入数据库
        UserDO newUserDO = new UserDO();
        newUserDO.setAccountNo(IDUtil.nextUserId());
        newUserDO.setNickName(userReq.getNickName());
        newUserDO.setPhone(userReq.getPhone());
//        //密码加密处理
//        newUserDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8)); //加密盐
//        newUserDO.setPwd(Md5Crypt.md5Crypt(userRegisterReq.getPwd().getBytes(), newUserDO.getSecret()));
        newUserDO.setPwd(passwordEncoder.encode(userReq.getPwd()));
        mapper.insertUserRole(RolesConst.DEFAULT_ROLE, newUserDO.getAccountNo());
        int rows = userMapper.insert(newUserDO);
        return rows;
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
    public int update(UserDO user) {
        if (user.getDel() != null && user.getDel()) {
            return 0;
        }
        if (StringUtils.isBlank(user.getAccountNo())) {
            return 0;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userDO.setAccountNo(user.getAccountNo());
        return userMapper.updateById(userDO);
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

    private Integer getTopRole(String accountNo) {
        List<String> roles_temp = userRoleMapper.getRoles(accountNo);
        if (roles_temp.size() == 0)
            return 8;//用户没有设置权限id，则默认返回8：游客
        List<Integer> roles = roles_temp.stream().map(Integer::parseInt).collect(Collectors.toList());
        return Collections.min(roles);
    }


}










