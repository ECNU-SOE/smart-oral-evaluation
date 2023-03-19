package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.UsrCourFilterReq;
import net.ecnu.manager.UserCourseManager;
import net.ecnu.mapper.UserCourseMapper;
import net.ecnu.model.UserCourseDO;
import net.ecnu.model.common.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserCourseManagerImpl implements UserCourseManager {
    @Autowired
    private UserCourseMapper userCourseMapper;

    @Override
    public UserCourseDO getByAccountNoAndCourseId(String accountNo, String courseId) {
        UserCourseDO userCourseDO = userCourseMapper.selectOne(new QueryWrapper<UserCourseDO>()
                .eq("account_no", accountNo)
                .eq("course_id", courseId)
        );
        return userCourseDO;
    }

    @Override
    public List<UserCourseDO> pageByFilter(UsrCourFilterReq userCourseFilter, PageData pageData) {
        return userCourseMapper.selectPage(new Page<UserCourseDO>(pageData.getCurrent(),pageData.getSize()),
                new QueryWrapper<UserCourseDO>()).getRecords();
    }

    @Override
    public int countByFilter(UsrCourFilterReq usrCourFilterReq) {
        return userCourseMapper.selectCount(new QueryWrapper<UserCourseDO>());
    }
}
