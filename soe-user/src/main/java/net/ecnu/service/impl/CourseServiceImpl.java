package net.ecnu.service.impl;

import net.ecnu.controller.request.CourFilterReq;
import net.ecnu.controller.request.CourAddReq;
import net.ecnu.controller.request.CourUpdateReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.manager.CourseManager;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.model.CourseDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.CourseVO;
import net.ecnu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseManager courseManager;
    @Override
    public Object add(CourAddReq courAddReq) {
        //检验数据库唯一性
        CourseDO  courseDO= courseMapper.selectById(courAddReq.getId());
        if (courseDO!=null)
            throw new BizException(BizCodeEnum.CLASS_REPEAT);
        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(loginUser.getRoleId()==null||loginUser.getRoleId()>3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        CourseDO csDO = new CourseDO();
        BeanUtils.copyProperties(courAddReq,csDO);
        csDO.setCreator(loginUser.getAccountNo());
        csDO.setDel(false);
        int rows = courseMapper.insert(csDO);
        return rows;
    }

    @Override
    public Object delete(String id) {
        //判断班级是否存在
        CourseDO courseDO = courseMapper.selectById(id);
        if (courseDO==null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(loginUser.getRoleId()==null||loginUser.getRoleId()>3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        int i = courseMapper.deleteById(id);
        return i;
    }

    @Override
    public Object update(CourUpdateReq courUpdateReq) {
        //判断班级是否存在
        CourseDO courseDO = courseMapper.selectById(courUpdateReq.getId());
        if (courseDO==null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(loginUser.getRoleId()==null||loginUser.getRoleId()>3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        CourseDO csDO = new CourseDO();
        BeanUtils.copyProperties(courUpdateReq,csDO);
        int rows = courseMapper.updateById(csDO);
        return rows;
    }

    @Override
    public Object pageByFilter(CourFilterReq courseFilter, PageData pageData) {
        List<CourseDO> courseDOS = courseManager.pageByFilter(courseFilter,pageData);
        int total = courseManager.countByFilter(courseFilter);
        pageData.setTotal(total);
        List<CourseVO> courseVOS = courseDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        pageData.setRecords(courseVOS);
        return pageData;
    }

    @Override
    public Object getById(String id) {
        CourseDO courseDO = courseMapper.selectById(id);
        if (courseDO==null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseDO,courseVO);
        return courseVO;
    }

    private CourseVO beanProcess(CourseDO courseDO) {
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseDO,courseVO);
        return courseVO;
    }


}
