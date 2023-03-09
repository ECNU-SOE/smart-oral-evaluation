package net.ecnu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.controller.request.CourseReq;
import net.ecnu.model.CourseDO;

public interface CourseService {
    Object create(CourseReq courseReq);
    Object delete(CourseReq courseReq);
    Object update(CourseReq courseReq);
//    Object getClasses();
    Object pageByFilter(CourseFilterReq courseFilter, Page<CourseDO> page);
}
