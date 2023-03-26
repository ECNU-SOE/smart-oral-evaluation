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
import net.ecnu.controller.request.UserReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.UserManager;
import net.ecnu.mapper.UserMapper;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.UserDO;
import net.ecnu.model.dto.UserDTO;
import net.ecnu.model.vo.UserVO;
import net.ecnu.service.UserService;
import net.ecnu.util.IDUtil;
import net.ecnu.util.JWTUtil;
import net.ecnu.utils.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserManager userManager;

    @Override
    public Object register(UserReq userReq) {
        //check手机验证码 与 数据库唯一性
        UserDO userDO = userManager.selectOneByPhone(userReq.getPhone());
        if (userDO != null) throw new BizException(BizCodeEnum.ACCOUNT_REPEAT);
        //处理生成userDO对象，插入数据库
        UserDO newUserDO = new UserDO();
        newUserDO.setAccountNo("user_" + IDUtil.getSnowflakeId());
        newUserDO.setNickName(userReq.getPhone());
        newUserDO.setPhone(userReq.getPhone());

//        //密码加密处理
//        newUserDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));
//        newUserDO.setPwd(Md5Crypt.md5Crypt(userRegisterReq.getPwd().getBytes(), newUserDO.getSecret()));

        newUserDO.setPwd(userReq.getPwd());
        int rows = userMapper.insert(newUserDO);
        return rows;
    }

    @Override
    public Object login(UserReq userReq) {
        UserDO userDO = userManager.selectOneByPhone(userReq.getPhone());
        //手机号不存在 || 密码错误
        if (userDO == null || !userDO.getPwd().equals(userReq.getPwd())) {
            throw new BizException(BizCodeEnum.ACCOUNT_PWD_ERROR);
        }
        //验证成功，生成token并返回
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(userDO, loginUser);
        return JWTUtil.geneJsonWebToken(loginUser);
    }

    @Override
    public Object getUserInfo() {
        //LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        //if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(StringUtils.isBlank(currentAccountNo)){
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //如果token有效，数据库查询用户个人信息
        UserDO userDO = userManager.selectOneByAccountNo(currentAccountNo);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
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


}










