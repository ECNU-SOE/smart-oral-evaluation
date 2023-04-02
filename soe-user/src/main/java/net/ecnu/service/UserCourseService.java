package net.ecnu.service;

import net.ecnu.controller.request.UsrCourAddReq;
import net.ecnu.controller.request.UsrCourFilterReq;
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

    Object add(UsrCourAddReq usrCourAddReq);
    Object delete(String id);
    Object list_user_cour(UserCourseDO userCourseDO);
}
