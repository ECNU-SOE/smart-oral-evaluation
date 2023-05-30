package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.constant.RolesConst;
import net.ecnu.controller.request.CourAddReq;
import net.ecnu.controller.request.CourFilterReq;
import net.ecnu.controller.request.CourUpdateReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.CourseManager;
import net.ecnu.mapper.ClassMapper;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.model.ClassDO;
import net.ecnu.model.CourseDO;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.CourseVO;
import net.ecnu.service.CourseService;
import net.ecnu.service.UserService;
import net.ecnu.util.IDUtil;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//import net.ecnu.interceptor.LoginInterceptor;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseManager courseManager;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private UserService userService;


    @Override
    public Object add(CourAddReq courAddReq) {
        //判断用户权限
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        Integer topRole = userService.getTopRole(currentAccountNo);
        //不允许插入重复课程名
        CourseDO courseDO = courseMapper.selectOne(new QueryWrapper<CourseDO>()
                .eq("name", courAddReq.getName())
        );
        if (courseDO!=null&& !courseDO.getDel())
            throw new BizException(BizCodeEnum.COURSE_REPEAT);
        if (Objects.equals(topRole, RolesConst.ROLE_SUPER_ADMIN) || Objects.equals(topRole, RolesConst.ROLE_SYSTEM_ADMIN) || Objects.equals(topRole, RolesConst.ROLE_ADMIN)) {
            CourseDO csDO = new CourseDO();
            BeanUtils.copyProperties(courAddReq, csDO);
            if (csDO.getPicture()==null|| StringUtils.isBlank(csDO.getPicture()))
                csDO.setPicture("https://img2.woyaogexing.com/2023/05/13/c898da777fb886a1aa9e2ae07a9e2296.jpg");
            csDO.setId(IDUtil.nextCourseId());
            csDO.setCreator(currentAccountNo);
            csDO.setDel(false);
            return courseMapper.insert(csDO);
        } else {
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
    }

    @Override
    public Object delete(String id) {
        //判断课程是否存在
        CourseDO courseDO = courseMapper.selectById(id);
        if (courseDO == null||courseDO.getDel())
            throw new BizException(BizCodeEnum.COURSE_UNEXISTS);
        //判断该课程下是否有班级，如果有则不能删除
        List<ClassDO> classDOS = classMapper.selectList(new QueryWrapper<ClassDO>()
                .eq("course_id", courseDO.getId())
        );
        if (classDOS.size() != 0)
            throw new BizException(BizCodeEnum.COURSE_USING);
        //判断用户权限
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        Integer topRole = userService.getTopRole(currentAccountNo);
        if (topRole <= RolesConst.ROLE_ADMIN) {
            CourseDO csDO = new CourseDO();
            BeanUtils.copyProperties(courseDO,csDO,"del");
            csDO.setDel(true);
            return courseMapper.updateById(csDO);
        } else {
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
    }

    @Override
    public Object update(CourUpdateReq courUpdateReq) {
        //判断课程是否存在
        CourseDO courseDO = courseMapper.selectById(courUpdateReq.getId());
        if (courseDO == null) throw new BizException(BizCodeEnum.COURSE_UNEXISTS);
        //判断用户权限
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        Integer topRole = userService.getTopRole(currentAccountNo);
        if (topRole <= RolesConst.ROLE_ADMIN) {
            BeanUtils.copyProperties(courUpdateReq, courseDO, "id");
            courseDO.setGmtModified(null);//更新时间
            int i = courseMapper.updateById(courseDO);
            return courseMapper.selectById(courseDO.getId());
        } else {
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
    public Object detail(String courseId) {
        CourseDO courseDO = courseMapper.selectOne(new QueryWrapper<CourseDO>()
                .eq("id", courseId)
                .eq("del", 0)
        );
        if (courseDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseDO, courseVO);
        return courseVO;
    }

    private CourseVO beanProcess(CourseDO courseDO) {
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseDO, courseVO);
        return courseVO;
    }

}
