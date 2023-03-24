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
import net.ecnu.mapper.UserRoleMapper;
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public Object add(UsrCourAddReq usrCourAddReq) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser==null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
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
        //权限判断
        if (Objects.equals(loginUser.getAccountNo(), usrCourAddReq.getAccountNo())){
            //登录用户选择自己的课程
            UserCourseDO userCourseDO = new UserCourseDO();
            BeanUtils.copyProperties(usrCourAddReq,userCourseDO);
            userCourseDO.setRType(userDO.getRoleId());
            return userCourseMapper.insert(userCourseDO);
        }else {
            //登录用户帮别人添加课程
            Integer role_A = getTopRole(loginUser.getAccountNo());
            Integer role_B = getTopRole(usrCourAddReq.getAccountNo());
            if (hasAddOrDelRight(role_A,role_B)){
                UserCourseDO userCourseDO = new UserCourseDO();
                BeanUtils.copyProperties(usrCourAddReq,userCourseDO);
                userCourseDO.setRType(userDO.getRoleId());
                return userCourseMapper.insert(userCourseDO);
            }else{
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            }
        }
    }

    @Override
    public Object delete(String id) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser==null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        //判断选课信息是否存在
        UserCourseDO userCourseDO = userCourseMapper.selectById(id);
        if (userCourseDO==null)
            throw new BizException(BizCodeEnum.USER_COURSE_UNEXISTS);
        //存在则权限校验
        if (Objects.equals(loginUser.getAccountNo(), userCourseDO.getAccountNo())){
            //登录用户删除本人选课信息
            return userCourseMapper.deleteById(id);
        }else {
            //登录用户删除别人选课信息
            Integer role_A = getTopRole(loginUser.getAccountNo());
            Integer role_B = getTopRole(userCourseDO.getAccountNo());
            if (hasAddOrDelRight(role_A,role_B)){
                return userCourseMapper.deleteById(id);
            }else {
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            }
        }
    }

    @Override
    public Object list_user_cour(UserCourseDO userCourseDO) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser==null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        if (Objects.equals(loginUser.getAccountNo(), userCourseDO.getAccountNo())){
            //登录用户查看自己的选课列表
            QueryWrapper<UserCourseDO> qw = new QueryWrapper<>();
            qw.eq("account_no",userCourseDO.getAccountNo());
            return userCourseMapper.selectList(qw);
        }else{
            //登录用户查看别人的选课列表
            Integer top_role1 = getTopRole(loginUser.getAccountNo());
            Integer top_role2 = getTopRole(userCourseDO.getAccountNo());
            if (hasListRight(top_role1,top_role2)){
                QueryWrapper<UserCourseDO> qw = new QueryWrapper<>();
                qw.eq("account_no",userCourseDO.getAccountNo());
                return userCourseMapper.selectList(qw);
            }else {
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            }
        }
    }

    //角色A是否有查看角色B的选课列表的权限
    private Boolean hasListRight(Integer role_A, Integer role_B){
        return role_A<role_B;
    }

    //获取当前用户的最高权限id,并强转为Integer型
    private Integer getTopRole(String accountNo){
        List<String> roles_temp = userRoleMapper.getRoles(accountNo);
        List<Integer> roles = roles_temp.stream().map(Integer::parseInt).collect(Collectors.toList());
        return Collections.min(roles);
    }
    //角色A是否有为角色B添加/删除课程的权限
    private Boolean hasAddOrDelRight(Integer role_A,Integer role_B){
        switch (role_A){
            case 1:
                return true;
            case 2:
                if(role_B>2)
                    return true;
            case 3:
                if (role_B>3)
                    return true;
            case 4:
                if (role_B>4)
                    return true;
            case 5:
                if (role_B>5)
                    return true;
            case 6:
                if (role_B>6)
                    return true;
            case 7:
                if (role_B == 7)
                    return true;
                else
                    return false;
        }
        return false;
    }
}
