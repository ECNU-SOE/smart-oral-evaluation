package net.ecnu.service.impl;

import net.ecnu.controller.request.CourseReq;
import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.model.CourseDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Override
    public Object create(CourseReq courseReq) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        CourseDO courseDO = new CourseDO();
        BeanUtils.copyProperties(courseReq,courseDO);
        courseDO.setCreator(loginUser.getAccountNo());
        int rows = courseMapper.insert(courseDO);
        return rows;
    }
}
