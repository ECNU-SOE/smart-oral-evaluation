package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.UserRegisterReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.UserManager;
import net.ecnu.mapper.UserMapper;
import net.ecnu.model.UserDO;
import net.ecnu.service.UserService;
import net.ecnu.util.CommonUtil;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserManager userManager;

    @Override
    public Object register(UserRegisterReq userRegisterReq) {
        //check手机验证码 与 数据库唯一性
        UserDO userDO = userManager.selectOneByPhone(userRegisterReq.getPhone());
        if (userDO != null) throw new BizException(BizCodeEnum.ACCOUNT_REPEAT);
        //处理生成userDO对象，插入数据库
        UserDO newUserDO = new UserDO();
        newUserDO.setAccountId("account_" + UUID.randomUUID());
        newUserDO.setNickName(userRegisterReq.getPhone());
        newUserDO.setPhone(userRegisterReq.getPhone());

//        //密码加密处理
//        newUserDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));
//        newUserDO.setPwd(Md5Crypt.md5Crypt(userRegisterReq.getPwd().getBytes(), newUserDO.getSecret()));

        newUserDO.setPwd(userRegisterReq.getPwd());
        int rows = userMapper.insert(newUserDO);
        return rows;
    }
}










