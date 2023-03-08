package net.ecnu.service.impl;

import net.ecnu.controller.request.CourseReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.manager.CourseManager;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.model.CourseDO;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.vo.CourseVO;
import net.ecnu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseManager courseManager;
    @Override
    public Object create(CourseReq courseReq) {
        //检验数据库唯一性
        CourseDO  courseDO= courseMapper.selectById(courseReq.getId());
        if (courseDO!=null)
            throw new BizException(BizCodeEnum.CLASS_RPEAT);

        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(loginUser.getRoleId()==null||loginUser.getRoleId()>3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        CourseDO csDO = new CourseDO();
        BeanUtils.copyProperties(courseReq,csDO);
        csDO.setCreator(loginUser.getAccountNo());
        csDO.setId(courseReq.getId());
        int rows = courseMapper.insert(csDO);
        return rows;
    }

    @Override
    public Object delete(CourseReq courseReq) {
        //判断班级是否存在
        CourseDO courseDO = courseMapper.selectById(courseReq.getId());
        if (courseDO==null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        LoginUser loginUser = LoginInterceptor.threadLocal.get();

        //判断用户权限
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(loginUser.getRoleId()==null||loginUser.getRoleId()>3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        int i = courseMapper.deleteById(courseReq.getId());
        return i;
    }

    @Override
    public Object update(CourseReq courseReq) {
        //判断班级是否存在
        CourseDO courseDO = courseMapper.selectById(courseReq.getId());
        if (courseDO==null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);

        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(loginUser.getRoleId()==null||loginUser.getRoleId()>3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        CourseDO csDO = new CourseDO();
        BeanUtils.copyProperties(courseReq,csDO);
        csDO.setCreator(loginUser.getAccountNo());
        csDO.setId(courseReq.getId());
        int rows = courseMapper.updateById(csDO);
        return rows;
    }

    @Override
    public Object getClasses() {
        List<CourseDO> courseDOS = courseMapper.selectList(null);
        List<CourseVO> courseVOs = courseDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        return courseVOs;
    }

    @Override
    public Object getCourses(CourseReq courseReq) {
        List<CourseDO> courseDOS = courseManager.selectAllByCourseId(courseReq.getCourseId());
        List<CourseVO> courseVOs = courseDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        return courseVOs;
    }

    private CourseVO beanProcess(CourseDO courseDO) {
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseDO,courseVO);
        return courseVO;
    }


}
