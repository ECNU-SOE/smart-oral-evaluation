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

    @Override
    public List<UserClassDO> pageByfilter(UsrClassFilterReq usrClassFilter, PageData pageData) {
        Page<UserClassDO> userClassDOPage = userClassMapper.selectPage(new Page<UserClassDO>(pageData.getCurrent(), pageData.getSize()), new QueryWrapper<UserClassDO>()
                .eq(!ObjectUtils.isEmpty(usrClassFilter.getClassId()), "class_id", usrClassFilter.getClassId())
                .eq(!ObjectUtils.isEmpty(usrClassFilter.getRType()), "r_type", usrClassFilter.getRType())
        );
        return userClassDOPage.getRecords();
    }

    @Override
    public int countByFilter(UsrClassFilterReq usrClassFilter) {
        Integer count = userClassMapper.selectCount(new QueryWrapper<UserClassDO>()
                .eq(!ObjectUtils.isEmpty(usrClassFilter.getClassId()), "class_id", usrClassFilter.getClassId())
                .eq(!ObjectUtils.isEmpty(usrClassFilter.getRType()), "r_type", usrClassFilter.getRType())
        );
        return count;
    }
}
