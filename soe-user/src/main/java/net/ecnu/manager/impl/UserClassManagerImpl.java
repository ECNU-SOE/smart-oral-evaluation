package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.UsrClassFilterReq;
import net.ecnu.manager.UserClassManager;
import net.ecnu.mapper.UserClassMapper;
import net.ecnu.model.UserClassDO;
import net.ecnu.model.common.PageData;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserClassManagerImpl implements UserClassManager {
    @Autowired
    private UserClassMapper userClassMapper;

    @Override
    public UserClassDO getByAccountNoAndClassId(String accountNo, String classId) {
        UserClassDO userClassDO = userClassMapper.selectOne(new QueryWrapper<UserClassDO>()
                .eq("account_no", accountNo)
                .eq("class_id", classId)
        );
        return userClassDO;
    }
}
