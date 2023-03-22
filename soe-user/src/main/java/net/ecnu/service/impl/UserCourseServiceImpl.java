package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.controller.request.UsrCourAddReq;
import net.ecnu.controller.request.UsrCourFilterReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.manager.UserCourseManager;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.mapper.UserMapper;
import net.ecnu.model.CourseDO;
import net.ecnu.model.UserCourseDO;
import net.ecnu.mapper.UserCourseMapper;
import net.ecnu.model.UserDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;
import net.ecnu.service.UserCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户-课程 关系表 服务实现类
 * </p>
 *
 * @author TGX
 * @since 2023-03-15
 */
@Service
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourseDO> implements UserCourseService {

    @Autowired
    private UserCourseMapper userCourseMapper;

    @Autowired
    private UserCourseManager userCourseManager;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public Object add(UsrCourAddReq usrCourAddReq) {
        //判断请求的用户正确性
        UserDO userDO = userMapper.selectById(usrCourAddReq.getAccountNo());
        if (userDO==null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        //判断请求的班级正确性
        CourseDO courseDO = courseMapper.selectById(usrCourAddReq.getCourseId());
        if (courseDO==null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断是否重复选课
        UserCourseDO userCourseDO1 = userCourseManager.getByAccountNoAndCourseId(usrCourAddReq.getAccountNo(),
                usrCourAddReq.getCourseId());
        if (userCourseDO1!=null)
            throw new BizException(BizCodeEnum.REPEAT_CHOOSE);
        UserCourseDO userCourseDO = new UserCourseDO();
        BeanUtils.copyProperties(usrCourAddReq,userCourseDO);
        userCourseDO.setRType(userDO.getRoleId());
        int rows = userCourseMapper.insert(userCourseDO);
        return rows;
    }

    @Override
    public Object delete(String id) {
        UserCourseDO userCourseDO = userCourseMapper.selectById(id);
        if (userCourseDO==null)
            throw new BizException(BizCodeEnum.USER_COURSE_UNEXISTS);
        int rows = userCourseMapper.deleteById(id);
        return rows;
    }

    @Override
    public Object pageByFilter(UsrCourFilterReq userCourseFilter, PageData pageData) {
        List<UserCourseDO> userCourseDOS = userCourseManager.pageByFilter(userCourseFilter, pageData);
        int total = userCourseManager.countByFilter(userCourseFilter);
        pageData.setTotal(total);
        pageData.setRecords(userCourseDOS);
        return pageData;
    }

    @Override
    public Object list_user_cour() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser==null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        QueryWrapper<UserCourseDO> qw = new QueryWrapper<>();
        qw.eq("account_no",loginUser.getAccountNo());
        List<UserCourseDO> userCourseDOS = userCourseMapper.selectList(qw);
        return userCourseDOS;
    }
}
