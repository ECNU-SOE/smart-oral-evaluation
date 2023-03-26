package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.constant.RolesConst;
import net.ecnu.controller.request.*;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
//import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.manager.CourseManager;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.mapper.UserCourseMapper;
import net.ecnu.mapper.UserRoleMapper;
import net.ecnu.mapper.UserMapper;
import net.ecnu.model.CourseDO;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.UserCourseDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.CourseVO;
import net.ecnu.model.vo.CpsgrpVO;
import net.ecnu.service.CourseService;
import net.ecnu.util.IDUtil;
import net.ecnu.utils.RequestParamUtil;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private UserCourseMapper userCourseMapper;

    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Autowired
    private CpsgrpManager cpsgrpManager;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Autowired
    private UserMapper userMapper;

    @Override
    public Object add(CourAddReq courAddReq) {
        //判断用户权限
        //LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        //if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        /*if (loginUser.getRoleId() == null || loginUser.getRoleId() > 3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);*/
        List<String> roles = userRoleMapper.getRoles(currentAccountNo);
        if(roles.contains(RolesConst.ROLE_SUPER_ADMIN) || roles.contains(RolesConst.ROLE_SYSTEM_ADMIN) || roles.contains(RolesConst.ROLE_ADMIN)){
            CourseDO csDO = new CourseDO();
            BeanUtils.copyProperties(courAddReq, csDO);
            csDO.setId(IDUtil.nextCourseId());
            csDO.setCreator(currentAccountNo);
            csDO.setDel(false);
            return courseMapper.insert(csDO);
        }else{
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
    }

    @Override
    public Object delete(String id) {
        //判断班级是否存在
        CourseDO courseDO = courseMapper.selectById(id);
        if (courseDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断用户权限
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        /*if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if (loginUser.getRoleId() == null || loginUser.getRoleId() > 3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);*/
        List<String> roles = userRoleMapper.getRoles(currentAccountNo);
        if(roles.contains(RolesConst.ROLE_SUPER_ADMIN) || roles.contains(RolesConst.ROLE_SYSTEM_ADMIN) || roles.contains(RolesConst.ROLE_ADMIN)){
            return courseMapper.deleteById(id);
        }else{
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
    }

    @Override
    public Object update(CourUpdateReq courUpdateReq) {
        //判断班级是否存在
        CourseDO courseDO = courseMapper.selectById(courUpdateReq.getId());
        if (courseDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断用户权限
        //LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        /*if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if (loginUser.getRoleId() == null || loginUser.getRoleId() > 3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);*/
        List<String> roles = userRoleMapper.getRoles(currentAccountNo);
        if(roles.contains(RolesConst.ROLE_SUPER_ADMIN) || roles.contains(RolesConst.ROLE_SYSTEM_ADMIN) || roles.contains(RolesConst.ROLE_ADMIN)){
            CourseDO csDO = new CourseDO();
            BeanUtils.copyProperties(courUpdateReq, csDO);
            return courseMapper.updateById(csDO);
        }else{
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
    }

    @Override
    public Object pageByFilter(CourFilterReq courseFilter, PageData pageData) {
        List<CourseDO> courseDOS = courseManager.pageByFilter(courseFilter, pageData);
        int total = courseManager.countByFilter(courseFilter);
        pageData.setTotal(total);
        List<CourseVO> courseVOS = courseDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        pageData.setRecords(courseVOS);
        return pageData;
    }

    @Override
    public Object addTest(TestAddReq testAddReq) {
        //LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        //判断班级是否存在
        CourseDO courseDO = courseMapper.selectById(testAddReq.getCourseId());
        if (courseDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断语料组是否异常
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(testAddReq.getCpsgrpId());
        if (cpsgrpDO == null || cpsgrpDO.getCourseId() != null)
            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        //身份校验,管理员可以直接发布，教师需要自己选了这门课程才能发布
        Integer topRole = getTopRole(currentAccountNo);
        if (topRole <= 3) {
            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
            BeanUtils.copyProperties(cpsgrpDO, cpsgrpDO1);
            cpsgrpDO1.setType(testAddReq.getType());
            cpsgrpDO1.setCourseId(testAddReq.getCourseId());
            return cpsgrpMapper.updateById(cpsgrpDO1);
        } else if (topRole > 3 && topRole <= 6) {
            UserCourseDO userCourseDO = userCourseMapper.selectOne(new QueryWrapper<UserCourseDO>()
                    .eq("account_no", currentAccountNo)
                    .eq("course_id", testAddReq.getCourseId()));
            if (userCourseDO == null)
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
            BeanUtils.copyProperties(cpsgrpDO, cpsgrpDO1);
            cpsgrpDO1.setType(testAddReq.getType());
            cpsgrpDO1.setCourseId(testAddReq.getCourseId());
            return cpsgrpMapper.updateById(cpsgrpDO1);
        } else
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
    }



    @Override
    public Object updateTest(TestUpdateReq testUpdateReq) {
        //LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        CourseDO courseDO = courseMapper.selectById(testUpdateReq.getCourseId());
        if (courseDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断语料组是否异常
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(testUpdateReq.getId());
        if (cpsgrpDO == null)
            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        //身份校验,管理员可以直接修改，教师需要自己选了这门课程才能修改
        Integer topRole = getTopRole(currentAccountNo);
        if (topRole <= 3) {
            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
            BeanUtils.copyProperties(testUpdateReq,cpsgrpDO1);
            return cpsgrpMapper.updateById(cpsgrpDO1);
        } else if (topRole > 3 && topRole <= 6) {
            UserCourseDO userCourseDO = userCourseMapper.selectOne(new QueryWrapper<UserCourseDO>()
                    .eq("account_no", currentAccountNo)
                    .eq("course_id", testUpdateReq.getCourseId()));
            if (userCourseDO == null)
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
            BeanUtils.copyProperties(testUpdateReq,cpsgrpDO1);
            return cpsgrpMapper.updateById(cpsgrpDO1);
        } else
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
    }

    @Override
    public Object delTest(String id) {
        //LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(id);
        if (cpsgrpDO == null)
            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        //语料组被加入课程才可删除
        if (cpsgrpDO.getCourseId()==null)
            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        Integer topRole = getTopRole(currentAccountNo);
        if (topRole <= 3) {
            return cpsgrpMapper.deleteById(id);
        } else if (topRole > 3 && topRole <= 6) {
            UserCourseDO userCourseDO = userCourseMapper.selectOne(new QueryWrapper<UserCourseDO>()
                    .eq("account_no", currentAccountNo)
                    .eq("course_id", cpsgrpDO.getCourseId()));
            if (userCourseDO == null)
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            return cpsgrpMapper.deleteById(id);
        } else
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
    }

    @Override
    public Object listTest(CpsgrpDO cpsgrpDO, PageData pageData) {
        List<CpsgrpDO> cpsgrpDOS = cpsgrpManager.pageByFilter(cpsgrpDO,pageData);
        int total = cpsgrpManager.countByFilter(cpsgrpDO);
        pageData.setTotal(total);
        List<CpsgrpVO> cpsgrpVOS = cpsgrpDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        pageData.setRecords(cpsgrpVOS);
        return pageData;
    }


    private CourseVO beanProcess(CourseDO courseDO) {
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseDO, courseVO);
        return courseVO;
    }

    private CpsgrpVO beanProcess(CpsgrpDO cpsgrpDO) {
            CpsgrpVO cpsgrpVO = new CpsgrpVO();
            BeanUtils.copyProperties(cpsgrpDO, cpsgrpVO);
            return cpsgrpVO;
    }

    private Integer getTopRole(String accountNo) {
        List<String> roles_temp = userRoleMapper.getRoles(accountNo);
        List<Integer> roles = roles_temp.stream().map(Integer::parseInt).collect(Collectors.toList());
        return Collections.min(roles);
    }

}
