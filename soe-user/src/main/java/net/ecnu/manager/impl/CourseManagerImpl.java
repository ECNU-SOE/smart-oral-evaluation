package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CourFilterReq;
import net.ecnu.manager.CourseManager;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.model.CourseDO;
import net.ecnu.model.common.PageData;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CourseManagerImpl implements CourseManager {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseDO> pageByFilter(CourFilterReq courseFilter, PageData pageData) {
        return courseMapper.selectPage(new Page<CourseDO>(pageData.getCurrent(), pageData.getSize()),
                new QueryWrapper<CourseDO>()
                        .eq(!ObjectUtils.isEmpty(courseFilter.getId()), "id", courseFilter.getId())
                        .like(!ObjectUtils.isEmpty(courseFilter.getName()), "name", courseFilter.getName())
                        .like(!ObjectUtils.isEmpty(courseFilter.getDescription()), "description", courseFilter.getDescription())
                        .eq(!ObjectUtils.isEmpty(courseFilter.getCreator()), "creator", courseFilter.getCreator())
                        .eq("del", 0)
        ).getRecords();
    }

    @Override
    public int countByFilter(CourFilterReq courseFilter) {
        return courseMapper.selectCount(new QueryWrapper<CourseDO>()
                .eq(!ObjectUtils.isEmpty(courseFilter.getId()), "id", courseFilter.getId())
                .like(!ObjectUtils.isEmpty(courseFilter.getName()), "name", courseFilter.getName())
                .like(!ObjectUtils.isEmpty(courseFilter.getDescription()), "description", courseFilter.getDescription())
                .eq(!ObjectUtils.isEmpty(courseFilter.getCreator()), "creator", courseFilter.getCreator())
                .eq("del", 0)
        );
    }
}
