package net.ecnu.service;

import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.controller.request.UserCourseCreateReq;
import net.ecnu.controller.request.UserCourseFilterReq;
import net.ecnu.model.UserCourseDO;
import com.baomidou.mybatisplus.extension.service.IService;
import net.ecnu.model.common.PageData;

/**
 * <p>
 * 用户-课程 关系表 服务类
 * </p>
 *
 * @author TGX
 * @since 2023-03-15
 */
public interface UserCourseService extends IService<UserCourseDO> {

    Object create(UserCourseCreateReq userCourseCreateReq);
    Object delete(String id);
    Object pageByFilter(UserCourseFilterReq userCourseFilter, PageData pageData);
}
