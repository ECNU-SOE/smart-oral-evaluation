package net.ecnu.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.UserReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.UserManager;
import net.ecnu.mapper.UserMapper;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.UserDO;
import net.ecnu.model.vo.UserVO;
import net.ecnu.service.UserService;
import net.ecnu.util.IDUtil;
import net.ecnu.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


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
    public Object info(HttpServletRequest req) {
        String token = req.getHeader("token");
        if (StringUtils.isBlank(token)){
            token = req.getParameter("token");
        }
        if (StringUtils.isBlank(token)){
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        }
        LoginUser loginUser = JWTUtil.checkJWT(token);
        UserDO userDO = userManager.selectOneByPhone(loginUser.getPhone());
        if (userDO==null){
            throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO,userVO);
        return userVO;
    }
}










