package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.UserFilterReq;
import net.ecnu.manager.UserManager;

import net.ecnu.mapper.UserMapper;
import net.ecnu.model.ClassDO;
import net.ecnu.model.UserDO;
import net.ecnu.model.common.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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

    @Override
    public UserDO selectOneByAccountNo(String accountNo) {
        return userMapper.selectOne(new QueryWrapper<UserDO>()
                .eq("account_no", accountNo)
                .eq("del", 0));
    }

    @Override
    public List<UserDO> pageByFilter(UserFilterReq userFilterReq, PageData pageData) {
        return userMapper.selectPage(new Page<UserDO>(pageData.getCurrent(), pageData.getSize()),
                new QueryWrapper<UserDO>()
                        .eq(!ObjectUtils.isEmpty(userFilterReq.getIdentifyId()), "identify_id", userFilterReq.getIdentifyId())
                        .like(!ObjectUtils.isEmpty(userFilterReq.getNickName()), "nick_name", userFilterReq.getNickName())
                        .like(!ObjectUtils.isEmpty(userFilterReq.getRealName()), "real_name", userFilterReq.getRealName())
                        .eq(!ObjectUtils.isEmpty(userFilterReq.getFirstLanguage()), "first_language", userFilterReq.getFirstLanguage())
                        .eq(!ObjectUtils.isEmpty(userFilterReq.getSex()), "sex", userFilterReq.getSex())
                        .eq(!ObjectUtils.isEmpty(userFilterReq.getBirth()), "birth", userFilterReq.getBirth())
                        .like(!ObjectUtils.isEmpty(userFilterReq.getPhone()), "phone", userFilterReq.getPhone())
                        .like(!ObjectUtils.isEmpty(userFilterReq.getMail()), "mail", userFilterReq.getMail())
                        .eq("del", 0)
        ).getRecords();
    }

    @Override
    public int countByFilter(UserFilterReq userFilterReq) {
        return userMapper.selectCount(new QueryWrapper<UserDO>()
                .eq(!ObjectUtils.isEmpty(userFilterReq.getIdentifyId()), "identify_id", userFilterReq.getIdentifyId())
                .like(!ObjectUtils.isEmpty(userFilterReq.getNickName()), "nick_name", userFilterReq.getNickName())
                .like(!ObjectUtils.isEmpty(userFilterReq.getRealName()), "real_name", userFilterReq.getRealName())
                .eq(!ObjectUtils.isEmpty(userFilterReq.getFirstLanguage()), "first_language", userFilterReq.getFirstLanguage())
                .eq(!ObjectUtils.isEmpty(userFilterReq.getSex()), "sex", userFilterReq.getSex())
                .eq(!ObjectUtils.isEmpty(userFilterReq.getBirth()), "birth", userFilterReq.getBirth())
                .like(!ObjectUtils.isEmpty(userFilterReq.getPhone()), "phone", userFilterReq.getPhone())
                .like(!ObjectUtils.isEmpty(userFilterReq.getMail()), "mail", userFilterReq.getMail())
                .eq("del", 0)
        );
    }
}
