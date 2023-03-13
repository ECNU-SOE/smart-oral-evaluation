package net.ecnu.manager;

import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.controller.request.UserCourseFilterReq;
import net.ecnu.model.CourseDO;
import net.ecnu.model.UserCourseDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface UserCourseManager {
    UserCourseDO getByAccountNoAndCourseId(String accountNo, String courseId);
    List<UserCourseDO> pageByFilter(UserCourseFilterReq userCourseFilter, PageData pageData);
    int countByFilter(UserCourseFilterReq userCourseFilterReq);
}
