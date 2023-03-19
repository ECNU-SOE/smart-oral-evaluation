package net.ecnu.manager;

import net.ecnu.controller.request.UsrCourFilterReq;
import net.ecnu.model.UserCourseDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface UserCourseManager {
    UserCourseDO getByAccountNoAndCourseId(String accountNo, String courseId);
    List<UserCourseDO> pageByFilter(UsrCourFilterReq userCourseFilter, PageData pageData);
    int countByFilter(UsrCourFilterReq usrCourFilterReq);
}
