package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.controller.request.CourseCreateReq;
import net.ecnu.controller.request.CourseUpdateReq;
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
    public Object create(CourseCreateReq courseCreateReq) {
        //检验数据库唯一性
        CourseDO  courseDO= courseMapper.selectById(courseCreateReq.getId());
        if (courseDO!=null)
            throw new BizException(BizCodeEnum.CLASS_RPEAT);

        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(loginUser.getRoleId()==null||loginUser.getRoleId()>3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        CourseDO csDO = new CourseDO();
        BeanUtils.copyProperties(courseCreateReq,csDO);
        csDO.setCreator(loginUser.getAccountNo());
        csDO.setId(courseCreateReq.getId());
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
        LoginUser loginUser = LoginInterceptor.threadLocal.get();

        //判断用户权限
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(loginUser.getRoleId()==null||loginUser.getRoleId()>3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        int i = courseMapper.deleteById(id);
        return i;
    }

    @Override
    public Object update(CourseUpdateReq courseUpdateReq) {
        //判断班级是否存在
        CourseDO courseDO = courseMapper.selectById(courseUpdateReq.getId());
        if (courseDO==null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);

        //判断用户权限
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNREGISTER);
        if(loginUser.getRoleId()==null||loginUser.getRoleId()>3)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        CourseDO csDO = new CourseDO();
        BeanUtils.copyProperties(courseUpdateReq,csDO);
        int rows = courseMapper.updateById(csDO);
        return rows;
    }

    @Override
    public Object pageByFilter(CourseFilterReq courseFilter,PageData pageData) {
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
