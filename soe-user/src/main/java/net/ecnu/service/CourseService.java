package net.ecnu.service;

import net.ecnu.controller.request.CourAddReq;
import net.ecnu.controller.request.CourFilterReq;
import net.ecnu.controller.request.CourUpdateReq;
import net.ecnu.model.common.PageData;

public interface CourseService {
    Object add(CourAddReq courAddReq);
    Object delete(String id);
    Object update(CourUpdateReq courUpdateReq);
    Object pageByFilter(CourFilterReq courseFilter, PageData pageData);
    Object detail(String classId);
}
