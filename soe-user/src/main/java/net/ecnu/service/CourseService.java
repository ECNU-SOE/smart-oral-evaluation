package net.ecnu.service;

import net.ecnu.controller.request.CourseReq;

public interface CourseService {
    Object create(CourseReq courseReq);
    Object delete(CourseReq courseReq);
    Object update(CourseReq courseReq);
    Object getClasses();
    Object getCourses(CourseReq courseReq);
}
