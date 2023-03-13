package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.controller.request.CourseCreateReq;
import net.ecnu.controller.request.CourseUpdateReq;
import net.ecnu.model.CourseDO;

public interface CourseService {
    Object create(CourseCreateReq courseCreateReq);
    Object delete(CourseCreateReq courseCreateReq);
    Object update(CourseUpdateReq courseUpdateReq);
    Object pageByFilter(CourseFilterReq courseFilter, Page<CourseDO> page);
}
