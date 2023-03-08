package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
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
    public List<CourseDO> selectAllByCourseId(String courseId) {
        List<CourseDO> courseDOS = courseMapper.selectList(new QueryWrapper<CourseDO>()
                .eq("course_id", courseId)
                .eq("del", 0));
        return courseDOS;
    }
}
