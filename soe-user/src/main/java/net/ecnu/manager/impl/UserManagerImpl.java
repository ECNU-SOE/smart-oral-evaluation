package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.manager.UserManager;

import net.ecnu.mapper.UserMapper;
import net.ecnu.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDO selectOneByPhone(String phone) {
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>()
                .eq("phone", phone)
                .eq("del", 0));
        return userDO;
    }

    @Override
    public List<UserDO> selectAllByPhone(String phone) {
        List<UserDO> userDOS = userMapper.selectList(new QueryWrapper<UserDO>()
                .eq("phone", phone)
                .eq("del", 0));
        return userDOS;
    }
}
