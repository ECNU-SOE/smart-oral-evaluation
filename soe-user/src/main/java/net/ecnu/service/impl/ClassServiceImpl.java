package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.controller.request.*;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.manager.ClassManager;
import net.ecnu.manager.UserClassManager;
import net.ecnu.mapper.*;
import net.ecnu.model.*;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.ClassVO;
import net.ecnu.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author TGX
 * @since 2023-04-10
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, ClassDO> implements ClassService {

    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassManager classManager;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserClassManager userClassManager;
    @Autowired
    private UserClassMapper userClassMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private CpsgrpMapper cpsgrpMapper;



    @Override
    public Object add(ClassAddReq classAddReq) {
        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if (loginUser.getRoleId() == null || loginUser.getRoleId() > 3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        //判断课程是否存在，存在才能插入
        CourseDO courseDO = courseMapper.selectById(classAddReq.getCourseId());
        if (courseDO == null)
            throw new BizException(BizCodeEnum.COURSE_UNEXISTS);
        //插入数据
        ClassDO classDO = new ClassDO();
        BeanUtils.copyProperties(classAddReq, classDO);
        classDO.setId("class_" + IDUtil.getSnowflakeId());
        classDO.setCreator(loginUser.getAccountNo());
        classDO.setDel(false);
        return classMapper.insert(classDO);
    }

    @Override
    public Object del(String id) {
        //判断班级是否存在
        ClassDO classDO = classMapper.selectById(id);
        if (classDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if (loginUser.getRoleId() == null || loginUser.getRoleId() > 3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        return classMapper.deleteById(id);
    }

    @Override
    public Object update(ClassUpdateReq classUpdateReq) {
        //判断班级是否存在
        ClassDO classDO = classMapper.selectById(classUpdateReq.getId());
        if (classDO==null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if (loginUser.getRoleId() == null || loginUser.getRoleId() > 3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        ClassDO csDO = new ClassDO();
        BeanUtils.copyProperties(classUpdateReq,csDO);
        return classMapper.updateById(csDO);
    }

    @Override
    public Object pageByFilter(ClassFilterReq classFilter, PageData pageData) {
        List<ClassDO> classDOS = classManager.pageByFilter(classFilter, pageData);
        int total = classManager.countByFilter(classFilter);
        pageData.setTotal(total);
        List<ClassVO> classVOS = classDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        pageData.setRecords(classVOS);
        return pageData;
    }

    @Override
    public Object addUsrClass(UsrClassAddReq usrClassAddReq) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        //判断请求的用户正确性
        UserDO userDO = userMapper.selectById(usrClassAddReq.getAccountNo());
        if (userDO == null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        //判断请求的班级正确性
        ClassDO classDO = classMapper.selectById(usrClassAddReq.getClassId());
        if (classDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断是否重复选课
        UserClassDO userClassDO = userClassManager.getByAccountNoAndClassId(usrClassAddReq.getAccountNo(),
                usrClassAddReq.getClassId());
        if (userClassDO != null)
            throw new BizException(BizCodeEnum.REPEAT_CHOOSE);
        //权限判断
        if (usrClassAddReq.getAccountNo()==null){
            //请求体accountNo为空，用户选择自己的课程（根据token）
            UserClassDO userClassDO1 = new UserClassDO();
            BeanUtils.copyProperties(usrClassAddReq,userClassDO1);
            userClassDO1.setRType(usrClassAddReq.getRType());//bug,需要手动设定rtype
            return userClassMapper.insert(userClassDO1);
        }else {
            //请求体accountNo不为空
            if (Objects.equals(loginUser.getAccountNo(), usrClassAddReq.getAccountNo())){
                //token的accountNo与请求体的accountNo相同，给自己选课
                UserClassDO userClassDO1 = new UserClassDO();
                BeanUtils.copyProperties(usrClassAddReq,userClassDO1);
                userClassDO1.setRType(usrClassAddReq.getRType());//bug,需要手动设定rtype
//                System.out.println("req的值是："+usrClassAddReq);
//                System.out.println("userClassDO1 的值是= " + userClassDO1);
                return userClassMapper.insert(userClassDO1);
            }else {
                //token的accountNo与请求体的accountNo不同，给别人选课
                Integer roleA = getTopRole(loginUser.getAccountNo());
                Integer roleB = getTopRole(usrClassAddReq.getAccountNo());
                if (hasAddOrDelRight(roleA,roleB)){
                    UserClassDO userClassDO1 = new UserClassDO();
                    BeanUtils.copyProperties(usrClassAddReq,userClassDO1);
                    return userClassMapper.insert(userClassDO1);
                }else {
                    throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
                }
            }
        }
    }

    @Override
    public Object delUsrClass(String id) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        //判断选课信息是否存在
        UserClassDO userClassDO = userClassMapper.selectById(id);
        if (userClassDO==null)
            throw new BizException(BizCodeEnum.USER_COURSE_UNEXISTS);
        //权限校验
        if (Objects.equals(loginUser.getAccountNo(),userClassDO.getAccountNo())){
            return userClassMapper.deleteById(id);
        }else {
            //删除别人选课信息
            Integer role_A = getTopRole(loginUser.getAccountNo());
            Integer role_B = getTopRole(userClassDO.getAccountNo());
            if (hasAddOrDelRight(role_A, role_B)) {
                return userClassMapper.deleteById(id);
            } else {
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            }
        }
    }

    @Override
    public Object listUsrClass(UserClassDO userClassDO) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        if (userClassDO.getAccountNo()==null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if (Objects.equals(loginUser.getAccountNo(), userClassDO.getAccountNo())) {
            //查看自己的选课列表
            QueryWrapper<UserClassDO> qw = new QueryWrapper<>();
            qw.eq("account_no", userClassDO.getAccountNo());
            return userClassMapper.selectList(qw);
        }
        else {
            //登录用户查看别人的选课列表
            Integer top_role1 = getTopRole(loginUser.getAccountNo());
            Integer top_role2 = getTopRole(userClassDO.getAccountNo());
            if (hasListRight(top_role1, top_role2)) {
                QueryWrapper<UserClassDO> qw = new QueryWrapper<>();
                qw.eq("account_no", userClassDO.getAccountNo());
                return userClassMapper.selectList(qw);
            } else {
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            }
        }
    }

    @Override
    public Object addTest(TestAddReq testAddReq) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null)
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        //判断班级是否存在
        ClassDO classDO = classMapper.selectById(testAddReq.getClassId());
        if (classDO==null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断语料组是否异常
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(testAddReq.getCpsgrpId());
        if (cpsgrpDO == null || cpsgrpDO.getClassId() != null)
            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        //身份校验,管理员可以直接发布，教师需要自己选了这门课程才能发布
        if (getTopRole(loginUser.getAccountNo()) <= 3) {
            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
            BeanUtils.copyProperties(cpsgrpDO, cpsgrpDO1);
            cpsgrpDO1.setType(testAddReq.getType());
            cpsgrpDO1.setClassId(testAddReq.getClassId());
            return cpsgrpMapper.updateById(cpsgrpDO1);
        } else if (getTopRole(loginUser.getAccountNo()) > 3 && getTopRole(loginUser.getAccountNo()) <= 6) {
            UserClassDO userClassDO = userClassMapper.selectOne(new QueryWrapper<UserClassDO>()
                    .eq("account_no", loginUser.getAccountNo())
                    .eq("class_id", testAddReq.getClassId()));
            if (userClassDO == null)
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
            BeanUtils.copyProperties(cpsgrpDO, cpsgrpDO1);
            cpsgrpDO1.setType(testAddReq.getType());
            cpsgrpDO1.setClassId(testAddReq.getClassId());
            return cpsgrpMapper.updateById(cpsgrpDO1);
        } else
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
    }

    //    @Override
//    public Object addTest(TestAddReq testAddReq) {
//        LoginUser loginUser = LoginInterceptor.threadLocal.get();
//        if (loginUser == null)
//            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
//        //判断班级是否存在
//        CourseDO courseDO = courseMapper.selectById(testAddReq.getCourseId());
//        if (courseDO == null)
//            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
//        //判断语料组是否异常
//        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(testAddReq.getCpsgrpId());
//        if (cpsgrpDO == null || cpsgrpDO.getCourseId() != null)
//            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        //身份校验,管理员可以直接发布，教师需要自己选了这门课程才能发布
//        if (getTopRole(loginUser.getAccountNo()) <= 3) {
//            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
//            BeanUtils.copyProperties(cpsgrpDO, cpsgrpDO1);
//            cpsgrpDO1.setType(testAddReq.getType());
//            cpsgrpDO1.setCourseId(testAddReq.getCourseId());
//            return cpsgrpMapper.updateById(cpsgrpDO1);
//        } else if (getTopRole(loginUser.getAccountNo()) > 3 && getTopRole(loginUser.getAccountNo()) <= 6) {
//            UserCourseDO userCourseDO = userCourseMapper.selectOne(new QueryWrapper<UserCourseDO>()
//                    .eq("account_no", loginUser.getAccountNo())
//                    .eq("course_id", testAddReq.getCourseId()));
//            if (userCourseDO == null)
//                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
//            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
//            BeanUtils.copyProperties(cpsgrpDO, cpsgrpDO1);
//            cpsgrpDO1.setType(testAddReq.getType());
//            cpsgrpDO1.setCourseId(testAddReq.getCourseId());
//            return cpsgrpMapper.updateById(cpsgrpDO1);
//        } else
//            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
//    }

    private ClassVO beanProcess(ClassDO classDO) {
        ClassVO classVO = new ClassVO();
        BeanUtils.copyProperties(classDO, classVO);
        return classVO;
    }

    //获取当前用户的最高权限id,并强转为Integer型
    private Integer getTopRole(String accountNo) {
        List<String> roles_temp = userRoleMapper.getRoles(accountNo);
        List<Integer> roles = roles_temp.stream().map(Integer::parseInt).collect(Collectors.toList());
        return Collections.min(roles);
    }

    //角色A是否有为角色B添加/删除课程的权限
    private Boolean hasAddOrDelRight(Integer role_A, Integer role_B) {
        switch (role_A) {
            case 1:
                return true;
            case 2:
                if (role_B > 2)
                    return true;
            case 3:
                if (role_B > 3)
                    return true;
            case 4:
                if (role_B > 4)
                    return true;
            case 5:
                if (role_B > 5)
                    return true;
            case 6:
                if (role_B > 6)
                    return true;
            case 7:
                return role_B == 7;
        }
        return false;
    }

    //角色A是否有查看角色B的选课列表的权限
    private Boolean hasListRight(Integer role_A, Integer role_B) {
        return role_A < role_B;
    }

}
