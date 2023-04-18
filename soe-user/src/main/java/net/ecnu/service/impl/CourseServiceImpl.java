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
import net.ecnu.mapper.UserRoleMapper;
import net.ecnu.model.ClassDO;
import net.ecnu.model.CourseDO;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.ClassVO;
import net.ecnu.model.vo.CourseVO;
import net.ecnu.service.CourseService;
import net.ecnu.util.IDUtil;
import net.ecnu.util.RequestParamUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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

//
//    @Autowired
//    private UserCourseMapper userCourseMapper;
//
//    @Autowired
//    private CpsgrpMapper cpsgrpMapper;
//
//    @Autowired
//    private CpsgrpManager cpsgrpManager;
//
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Object add(CourAddReq courAddReq) {
        //判断用户权限
        String currentAccountNo = RequestParamUtil.currentAccountNo();
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
        //判断课程是否存在
        CourseDO courseDO = courseMapper.selectById(id);
        if (courseDO == null)
            throw new BizException(BizCodeEnum.COURSE_UNEXISTS);
        //判断该课程下是否有班级，如果有则不能删除
        List<ClassDO> classDOS = classMapper.selectList(new QueryWrapper<ClassDO>()
                .eq("course_id", courseDO.getId())
        );
        if (classDOS.size() != 0)
            throw new BizException(BizCodeEnum.COURSE_USING);
        //判断用户权限
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        Integer topRole = getTopRole(currentAccountNo);
        if(topRole <= RolesConst.ROLE_ADMIN){
            return courseMapper.deleteById(id);
        }else{
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
    }

    @Override
    public Object update(CourUpdateReq courUpdateReq) {
        //判断课程是否存在
        CourseDO courseDO = courseMapper.selectById(courUpdateReq.getId());
        if (courseDO == null)
            throw new BizException(BizCodeEnum.COURSE_UNEXISTS);
        //判断用户权限
        //LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        Integer topRole = getTopRole(currentAccountNo);
        if(topRole <= RolesConst.ROLE_ADMIN){
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
    public Object detail(String courseId) {
        CourseDO courseDO = courseMapper.selectOne(new QueryWrapper<CourseDO>()
                .eq("id",courseId)
                .eq("del",0)
        );
        if (courseDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        CourseVO courseVO =new CourseVO();
        BeanUtils.copyProperties(courseDO,courseVO);
        return courseVO;
    }

    //
//
//
//
    private CourseVO beanProcess(CourseDO courseDO) {
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseDO, courseVO);
        return courseVO;
    }
//
//    private CpsgrpVO beanProcess(CpsgrpDO cpsgrpDO) {
//            CpsgrpVO cpsgrpVO = new CpsgrpVO();
//            BeanUtils.copyProperties(cpsgrpDO, cpsgrpVO);
//            return cpsgrpVO;
//    }
//
    private Integer getTopRole(String accountNo) {
        List<String> roles_temp = userRoleMapper.getRoles(accountNo);
        List<Integer> roles = roles_temp.stream().map(Integer::parseInt).collect(Collectors.toList());
        return Collections.min(roles);
    }

}
