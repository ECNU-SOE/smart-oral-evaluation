package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.manager.UserClassManager;
import net.ecnu.mapper.UserClassMapper;
import net.ecnu.model.UserClassDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

//    @Override
//    public List<UserClassDO> pageByFilter(UsrClassFilterReq userCourseFilter, PageData pageData) {
//        return userCourseMapper.selectPage(new Page<UserCourseDO>(pageData.getCurrent(),pageData.getSize()),
//                new QueryWrapper<UserCourseDO>()).getRecords();
//    }
//
//    @Override
//    public int countByFilter(UsrCourFilterReq usrCourFilterReq) {
//        return userCourseMapper.selectCount(new QueryWrapper<UserCourseDO>());
//    }
}
