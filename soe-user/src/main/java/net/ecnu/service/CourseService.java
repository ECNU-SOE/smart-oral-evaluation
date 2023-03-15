package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.controller.request.CourseCreateReq;
import net.ecnu.controller.request.CourseUpdateReq;
import net.ecnu.model.CourseDO;
import net.ecnu.model.common.PageData;

public interface CourseService {
    Object create(CourseCreateReq courseCreateReq);
    Object delete(String id);
    Object update(CourseUpdateReq courseUpdateReq);
    Object pageByFilter(CourseFilterReq courseFilter, PageData pageData);
    Object getById(String id);
}
