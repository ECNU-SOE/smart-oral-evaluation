package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.manager.CourseManager;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.model.CourseDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CourseManagerImpl implements CourseManager {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseDO> selectByCourseId(String courseId) {
        List<CourseDO> courseDOS = courseMapper.selectList(new QueryWrapper<CourseDO>()
                .eq("course_id", courseId)
                .eq("del", 0));
        return courseDOS;
    }

    @Override
    public IPage<CourseDO> pageByFilter(CourseFilterReq courseFilter, Page<CourseDO> courseDOPage) {
        return courseMapper.selectPage(courseDOPage,new QueryWrapper<CourseDO>()
                        .eq("del",0)
                        .eq("id",courseFilter.getId())
                );
    }
}
