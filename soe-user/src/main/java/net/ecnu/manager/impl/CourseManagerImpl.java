package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.manager.CourseManager;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.model.CourseDO;
import net.ecnu.model.common.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CourseManagerImpl implements CourseManager {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseDO> pageByFilter(CourseFilterReq courseFilter, PageData pageData) {
        return courseMapper.selectPage(new Page<CourseDO>(pageData.getCurrent(),pageData.getSize()),
                new QueryWrapper<CourseDO>()
                .eq("del", 0)
        ).getRecords();
    }

    @Override
    public int countByFilter(CourseFilterReq courseFilterReq) {
        return courseMapper.selectCount(new QueryWrapper<CourseDO>()
                .eq("del",0)
        );
    }
}
