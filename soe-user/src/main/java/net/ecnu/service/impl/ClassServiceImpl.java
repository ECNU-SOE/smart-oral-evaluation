package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.constant.RolesConst;
import net.ecnu.controller.request.*;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.ClassManager;
import net.ecnu.manager.UserClassManager;
import net.ecnu.mapper.*;
import net.ecnu.model.*;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.ClassVO;
import net.ecnu.model.vo.ClassVO_LYW;
import net.ecnu.model.vo.UserClassVO;
import net.ecnu.service.ClassService;
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
    private ClassCpsgrpMapper classCpsgrpMapper;
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
    @Autowired
    private UserService userService;

    @Override
    public Object add(ClassAddReq classAddReq) {
        //判断用户权限
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (!checkPermission(currentAccountNo)) {
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
        //判断课程是否存在，存在才能插入
        CourseDO courseDO = courseMapper.selectById(classAddReq.getCourseId());
        if (courseDO == null||courseDO.getDel())
            throw new BizException(BizCodeEnum.COURSE_UNEXISTS);

        //名字重复复校验
        ClassDO classDO1 = classMapper.selectOne(new QueryWrapper<ClassDO>()
                .eq("name", classAddReq.getName())
        );
        if (classDO1!=null&&!classDO1.getDel())
            throw new BizException(BizCodeEnum.CLASS_REPEAT);
        //插入数据
        ClassDO classDO = new ClassDO();
        BeanUtils.copyProperties(classAddReq, classDO);
        //如没有班级图片则赋予默认值
        String picUrl = "https://img2.woyaogexing.com/2023/05/15/289be248ba0940546190f685063d7dca.jpg";
        if (classDO.getPicture()==null||StringUtils.isBlank(picUrl))
            classDO.setPicture(picUrl);
        classDO.setId("class_" + IDUtil.getSnowflakeId());
        classDO.setCreator(currentAccountNo);
        classDO.setDel(false);
        return classMapper.insert(classDO);
    }

    @Override
    public Object del(String id) {
        //判断班级是否存在
        ClassDO classDO = classMapper.selectById(id);
        if (classDO == null|| classDO.getDel())
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断用户权限
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (!checkPermission(currentAccountNo)) {
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
        //判断该班级是否存在选课信息
        ClassDO newClassDO= new ClassDO();
        BeanUtils.copyProperties(classDO,newClassDO,"del");
        newClassDO.setDel(true);
        return classMapper.updateById(newClassDO);
    }

    @Override
    public Object update(ClassUpdateReq classUpdateReq) {
        //判断班级是否存在
        ClassDO classDO = classMapper.selectById(classUpdateReq.getId());
        if (classDO == null||classDO.getDel())
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断用户权限
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (!checkPermission(currentAccountNo)) {
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }

        ClassDO csDO = new ClassDO();
        BeanUtils.copyProperties(classUpdateReq, csDO);
        return classMapper.updateById(csDO);
    }

    @Override
    public Object pageByFilter(ClassFilterReq classFilter, PageData pageData) {
        if (classFilter.getCpsgrpId()==null||StringUtils.isBlank(classFilter.getCpsgrpId())){
            //cpsgrp为空，按条件查询
            List<ClassDO> classDOS = classManager.pageByFilter(classFilter, pageData);
            int total = classManager.countByFilter(classFilter);
            pageData.setTotal(total);
            List<ClassVO> classVOS = classDOS.stream().map(this::beanProcess).collect(Collectors.toList());
            classVOS.forEach(classVO->{
                //向classVO中部分聚合courseDO属性
                CourseDO courseDO = courseMapper.selectById(classVO.getCourseId());
                if (courseDO == null) throw new BizException(BizCodeEnum.COURSE_UNEXISTS);
                classVO.setCourseName(courseDO.getName());
            });
            pageData.setRecords(classVOS);
            return pageData;
        }else {
            //cpsgrp不为空，按cpsgrp查询
            List<ClassCpsgrpDO> classCpsgrpDos = classCpsgrpMapper.selectList(new QueryWrapper<ClassCpsgrpDO>()
                    .eq("cpsgrp_id", classFilter.getCpsgrpId())
            );
            if (classCpsgrpDos.size()==0)
                return "暂无使用此套语料组的班级";
            List<String> classIds = classCpsgrpDos.stream().map(ClassCpsgrpDO::getClassId).collect(Collectors.toList());
            List<ClassDO> classDOS = classMapper.selectBatchIds(classIds);
            int total = classIds.size();
            List<ClassVO> classVOS = classDOS.stream().map(this::beanProcess).collect(Collectors.toList());
            classVOS.forEach(classVO->{
                //向classVO中部分聚合courseDO属性
                CourseDO courseDO = courseMapper.selectById(classVO.getCourseId());
                if (courseDO == null) throw new BizException(BizCodeEnum.COURSE_UNEXISTS);
                classVO.setCourseName(courseDO.getName());
            });
            pageData.setRecords(classVOS);
            return pageData;
        }


    }

    @Override
    public Object addUsrClass(UsrClassReq usrClassReq) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //判断是否重复选课
        UserClassDO userClassDO = userClassManager.getByAccountNoAndClassId(usrClassReq.getAccountNo(),
                usrClassReq.getClassId());
        if (userClassDO != null)
            throw new BizException(BizCodeEnum.REPEAT_CHOOSE);
        //权限判断
        if (usrClassReq.getAccountNo() == null||StringUtils.isBlank(usrClassReq.getAccountNo())) {
            //请求体accountNo为空，用户选择自己的课程（根据token）
            UserClassDO userClassDO1 = new UserClassDO();
            BeanUtils.copyProperties(usrClassReq, userClassDO1);
            userClassDO1.setAccountNo(currentAccountNo);
            userClassDO1.setRType(usrClassReq.getRType());//bug,需要手动设定rtype
            return userClassMapper.insert(userClassDO1);
        } else {
            //请求体accountNo不为空
            if (Objects.equals(currentAccountNo, usrClassReq.getAccountNo())) {
                //token的accountNo与请求体的accountNo相同，给自己选课
                UserClassDO userClassDO1 = new UserClassDO();
                BeanUtils.copyProperties(usrClassReq, userClassDO1);
                userClassDO1.setRType(usrClassReq.getRType());//bug,需要手动设定rtype
                return userClassMapper.insert(userClassDO1);
            } else {
                //token的accountNo与请求体的accountNo不同，给别人选课
                Integer role = userService.getTopRole(currentAccountNo);
                if (role<RolesConst.ROLE_ADMIN){//系统管理员及以上直接选课
                    UserClassDO userClassDO1 = new UserClassDO();
                    BeanUtils.copyProperties(usrClassReq, userClassDO1);
                    return userClassMapper.insert(userClassDO1);
                }else {//老师给自己所在班级选课
                    UserClassDO userClassDo = userClassManager.getByAccountNoAndClassId(currentAccountNo, usrClassReq.getClassId());
                    if (userClassDo==null||userClassDo.getRType()==1||userClassDo.getRType()==2)//1：学生，2：助教
                        throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
                    UserClassDO userClassDO1 = new UserClassDO();
                    BeanUtils.copyProperties(usrClassReq, userClassDO1);
                    return userClassMapper.insert(userClassDO1);
                }
            }
        }
    }

    @Override
    public Object updateUsrClass(UsrClassReq usrClassReq) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //权限判断
        if (usrClassReq.getAccountNo() == null||StringUtils.isBlank(usrClassReq.getAccountNo())) {
            //请求体accountNo为空，用户更新自己的课程关系信息，只更新rType
            UserClassDO userClassDO = userClassManager.getByAccountNoAndClassId(currentAccountNo, usrClassReq.getClassId());
            if (userClassDO==null)
                throw new BizException(BizCodeEnum.USER_COURSE_UNEXISTS);
            UserClassDO newUserClassDO = new UserClassDO();
            BeanUtils.copyProperties(userClassDO, newUserClassDO,"r_type");
            newUserClassDO.setRType(usrClassReq.getRType());
            return userClassMapper.updateById(newUserClassDO);
        } else {
            //请求体accountNo不为空
            if (Objects.equals(currentAccountNo, usrClassReq.getAccountNo())) {
                //token的accountNo与请求体的accountNo相同，用户更新自己的课程关系信息
                UserClassDO userClassDO = userClassManager.getByAccountNoAndClassId(currentAccountNo, usrClassReq.getClassId());
                if (userClassDO==null)
                    throw new BizException(BizCodeEnum.USER_COURSE_UNEXISTS);
                UserClassDO newUserClassDO = new UserClassDO();
                BeanUtils.copyProperties(userClassDO, newUserClassDO,"r_type");
                newUserClassDO.setRType(usrClassReq.getRType());
                return userClassMapper.updateById(newUserClassDO);
            } else {
                UserClassDO userClassDO = userClassManager.getByAccountNoAndClassId(usrClassReq.getAccountNo(), usrClassReq.getClassId());
                if (userClassDO==null)
                    throw new BizException(BizCodeEnum.USER_COURSE_UNEXISTS);
                //token的accountNo与请求体的accountNo不同，给别人更新课程关系信息
                Integer role = userService.getTopRole(currentAccountNo);
                if (role<RolesConst.ROLE_ADMIN){//系统管理员及以上直接更新
                    UserClassDO newUserClassDO = new UserClassDO();
                    BeanUtils.copyProperties(userClassDO, newUserClassDO,"r_type");
                    newUserClassDO.setRType(usrClassReq.getRType());
                    return userClassMapper.updateById(newUserClassDO);
                }else {//老师给自己所在班级更新
                    UserClassDO userClassDo = userClassManager.getByAccountNoAndClassId(currentAccountNo, usrClassReq.getClassId());
                    if (userClassDo==null||userClassDo.getRType()==1||userClassDo.getRType()==2)//1：学生，2：助教
                        throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
                    UserClassDO newUserClassDO = new UserClassDO();
                    BeanUtils.copyProperties(userClassDO, newUserClassDO,"r_type");
                    newUserClassDO.setRType(usrClassReq.getRType());
                    return userClassMapper.updateById(newUserClassDO);
                }
            }
        }
    }

    @Override
    public Object addUsrClassBatch(UsrClassAddBatchReq usrClassAddBatchReq) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //权限判断
        boolean hasRight = false;
        Integer role = userService.getTopRole(currentAccountNo);
        if (role<RolesConst.ROLE_ADMIN)
            hasRight = true;
        UserClassDO currentDo = userClassManager.getByAccountNoAndClassId(currentAccountNo, usrClassAddBatchReq.getClassId());
        if (currentDo!=null&&(currentDo.getRType()==3||currentDo.getRType()==4))//3老师 4管理员
            hasRight = true;
        if (!hasRight)
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);

        //判断是否重复选课
        for (int i = 0;i<usrClassAddBatchReq.getAccountNoList().size();i++){
            UserClassDO userClassDO = userClassManager.getByAccountNoAndClassId(usrClassAddBatchReq.getAccountNoList().get(i), usrClassAddBatchReq.getClassId());
            if (userClassDO!=null)
                return "用户"+userClassDO.getAccountNo()+"重复选课";
        }
        if (usrClassAddBatchReq.getAccountNoList().size()==0)
            return "未选择用户";
        //批量添加用户
        int count = 0;
        for (int i = 0;i<usrClassAddBatchReq.getAccountNoList().size();i++){
            UserClassDO userClassDO = new UserClassDO();
            userClassDO.setAccountNo(usrClassAddBatchReq.getAccountNoList().get(i));
            userClassDO.setClassId(usrClassAddBatchReq.getClassId());
            userClassDO.setRType(1);//默认为学生
            userClassMapper.insert(userClassDO);
            count++;
        }
        return count;
    }

    @Override
    public Object delUsrClass(String id) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //判断选课信息是否存在
        UserClassDO userClassDO = userClassMapper.selectById(id);
        if (userClassDO == null)
            throw new BizException(BizCodeEnum.USER_COURSE_UNEXISTS);
        //权限校验
        if (Objects.equals(currentAccountNo, userClassDO.getAccountNo())) {
            return userClassMapper.deleteById(id);
        } else {
            //删除别人选课信息
            Integer role = userService.getTopRole(currentAccountNo);
            if (role<RolesConst.ROLE_ADMIN) {
                return userClassMapper.deleteById(id);
            } else {
                UserClassDO userClassDo = userClassManager.getByAccountNoAndClassId(currentAccountNo, userClassDO.getClassId());
                if (userClassDo==null||userClassDo.getRType()==1||userClassDo.getRType()==2)//1：学生，2：助教
                    throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
                return userClassMapper.deleteById(id);
            }
        }
    }

    @Override
    public Object addTest(TestAddReq testAddReq) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //判断班级是否存在
        ClassDO classDO = classMapper.selectById(testAddReq.getClassId());
        if (classDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        //判断语料组是否异常
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(testAddReq.getCpsgrpId());
        if (cpsgrpDO == null || cpsgrpDO.getClassId() != null)
            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        //身份校验,管理员可以直接发布，教师需要自己选了这门课程才能发布
        Integer topRole = userService.getTopRole(currentAccountNo);
        if (topRole <= RolesConst.ROLE_ADMIN) {
            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
            BeanUtils.copyProperties(cpsgrpDO, cpsgrpDO1);
            cpsgrpDO1.setType(testAddReq.getType());
            cpsgrpDO1.setClassId(testAddReq.getClassId());
            return cpsgrpMapper.updateById(cpsgrpDO1);
        } else if (topRole <= RolesConst.TRAINER_C) {
            UserClassDO userClassDO = userClassMapper.selectOne(new QueryWrapper<UserClassDO>()
                    .eq("account_no", currentAccountNo)
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

    @Override
    public Object addCpsgrp(ClassCpsgrpReq classCpsgrpReq) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        ClassCpsgrpDO classCpsgrpDO = classCpsgrpMapper.selectOne(new QueryWrapper<ClassCpsgrpDO>()
                .eq("class_id", classCpsgrpReq.getClassId())
                .eq("cpsgrp_id", classCpsgrpReq.getCpsgrpId())
        );
        if (classCpsgrpDO!=null)
            throw new BizException(BizCodeEnum.CORPUS_ADD_ERROR);
        ClassCpsgrpDO classCpsgrpDO1 = new ClassCpsgrpDO();
        BeanUtils.copyProperties(classCpsgrpReq,classCpsgrpDO1);
        int total = classCpsgrpMapper.insert(classCpsgrpDO1);
        return total;
    }

    @Override
    public Object delTest(String id) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(id);
        if (cpsgrpDO == null)
            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        //语料组被加入班级才可删除
        if (cpsgrpDO.getClassId() == null)
            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        Integer topRole = userService.getTopRole(currentAccountNo);
        if (topRole <= RolesConst.ROLE_ADMIN) {
            return cpsgrpMapper.deleteById(id);
        } else if (topRole <= RolesConst.TRAINER_C) {
            UserClassDO userClassDO = userClassMapper.selectOne(new QueryWrapper<UserClassDO>()
                    .eq("account_no", currentAccountNo)
                    .eq("class_id", cpsgrpDO.getClassId()));
            if (userClassDO == null)
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            return cpsgrpMapper.deleteById(id);
        } else
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
    }

    @Override
    public Object updateTest(TestUpdateReq testUpdateReq) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        //判断语料组是否异常
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(testUpdateReq.getId());
        if (cpsgrpDO == null || cpsgrpDO.getClassId() == null)
            throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        //身份校验,管理员可以直接修改，教师需要自己选了这门课程才能修改
        Integer topRole = userService.getTopRole(currentAccountNo);
        if (topRole <= RolesConst.ROLE_ADMIN) {
            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
            BeanUtils.copyProperties(testUpdateReq, cpsgrpDO1);
            return cpsgrpMapper.updateById(cpsgrpDO1);
        } else if (topRole <= RolesConst.TRAINER_C) {
            UserClassDO userClassDO = userClassMapper.selectOne(new QueryWrapper<UserClassDO>()
                    .eq("account_no", currentAccountNo));
            if (userClassDO == null)
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            CpsgrpDO cpsgrpDO1 = new CpsgrpDO();
            BeanUtils.copyProperties(testUpdateReq, cpsgrpDO1);
            return cpsgrpMapper.updateById(cpsgrpDO1);
        } else
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
    }

    @Override
    public Object detail(String classId) {
        ClassDO classDO = classMapper.selectOne(new QueryWrapper<ClassDO>()
                .eq("id", classId)
                .eq("del", 0)
        );
        if (classDO == null)
            throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
        UserClassDO userClassDO = userClassMapper.selectOne(new QueryWrapper<UserClassDO>()
                .eq("class_id", classDO.getId())
                .eq("r_type", 3)//3:老师
        );
        ClassVO classVO = new ClassVO();
        if (userClassDO!=null)
            classVO.setTeacher(userClassDO.getAccountNo());
        BeanUtils.copyProperties(classDO, classVO);
        return classVO;
    }

    @Override
    public Object listSel(String accountNo) {
        //获取登录用户的accountNo
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        if (accountNo==null||StringUtils.isBlank(accountNo)||Objects.equals(accountNo,currentAccountNo)){
            //查自己的班级信息
            List<UserClassDO> userClassDOS = userClassMapper.selectList(new QueryWrapper<UserClassDO>()
                    .eq("account_no", currentAccountNo));
            //classVO聚合userClass信息
            List<ClassVO_LYW> classVOs = userClassDOS.stream().map(this::beanProcess).collect(Collectors.toList());
            //classVO聚合class信息
            classVOs.forEach(classVO -> {
                //向classVO中部分聚合classDO属性
                ClassDO classDO = classMapper.selectById(classVO.getClassId());
                if (classDO == null) throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
                BeanUtils.copyProperties(classDO, classVO, "id", "name", "creator", "del");
                classVO.setClassName(classDO.getName());
                //向classVO中部分聚合courseDO属性
                CourseDO courseDO = courseMapper.selectById(classDO.getCourseId());
                if (courseDO == null) throw new BizException(BizCodeEnum.COURSE_UNEXISTS);
                classVO.setCourseName(courseDO.getName());
            });
            return classVOs;
        }else {
            //查别人
            Integer userRole = userService.getTopRole(accountNo);
            Integer currentRole = userService.getTopRole(currentAccountNo);
            if (!hasListRight(currentRole,userRole))
                throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
            List<UserClassDO> userClassDOS = userClassMapper.selectList(new QueryWrapper<UserClassDO>()
                    .eq("account_no", accountNo));
            //classVO聚合userClass信息
            List<ClassVO_LYW> classVOs = userClassDOS.stream().map(this::beanProcess).collect(Collectors.toList());
            //classVO聚合class信息
            classVOs.forEach(classVO -> {
                //向classVO中部分聚合classDO属性
                ClassDO classDO = classMapper.selectById(classVO.getClassId());
                if (classDO == null) throw new BizException(BizCodeEnum.CLASS_UNEXISTS);
                BeanUtils.copyProperties(classDO, classVO, "id", "name", "creator", "del");
                classVO.setClassName(classDO.getName());
                //向classVO中部分聚合courseDO属性
                CourseDO courseDO = courseMapper.selectById(classDO.getCourseId());
                if (courseDO == null) throw new BizException(BizCodeEnum.COURSE_UNEXISTS);
                classVO.setCourseName(courseDO.getName());
            });
            return classVOs;
        }
    }

    @Override
    public Object listMem(UsrClassFilterReq usrClassFilter, PageData pageData) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (StringUtils.isBlank(currentAccountNo)) {
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION);
        }
        int i = userClassManager.countByFilter(usrClassFilter);
        pageData.setTotal(i);
        List<UserClassDO> userClassDOs = userClassManager.pageByfilter(usrClassFilter, pageData);
        //聚合rType和gmtCreate
        List<UserClassVO> userClassVOS = userClassDOs.stream().map(this::beanProcess2).collect(Collectors.toList());
        //聚合userVO和courseId
        userClassVOS.forEach(userClassVO -> {
            UserDO userDO = userMapper.selectById(userClassVO.getAccountNo());
            BeanUtils.copyProperties(userDO,userClassVO);
            ClassDO classDO = classMapper.selectById(userClassVO.getClassId());
            userClassVO.setCourseId(classDO.getCourseId());
        });

        pageData.setRecords(userClassVOS);
        return pageData;
    }


    private Boolean checkPermission(String accountNo) {
        Integer topRole = userService.getTopRole(accountNo);
        if (topRole > RolesConst.ROLE_ADMIN) {
            return false;
        }
        return true;
    }

    private ClassVO beanProcess(ClassDO classDO) {
        ClassVO classVO = new ClassVO();
        BeanUtils.copyProperties(classDO, classVO);
        return classVO;
    }

    private ClassVO_LYW beanProcess(UserClassDO userClassDO) {
        ClassVO_LYW classVO = new ClassVO_LYW();
        BeanUtils.copyProperties(userClassDO, classVO);
        return classVO;
    }

    private UserClassVO beanProcess2(UserClassDO userClassDO) {
        UserClassVO userClassVO = new UserClassVO();
        BeanUtils.copyProperties(userClassDO, userClassVO);
        return userClassVO;
    }

    private UserClassVO beanProcess(UserDO userDO){
        UserClassVO userClassVO = new UserClassVO();
        BeanUtils.copyProperties(userDO,userClassVO,"gmtCreate");
        return userClassVO;
    }

    //角色A是否有查看角色B的选课列表的权限
    private Boolean hasListRight(Integer role_A, Integer role_B) {
        if (role_A<=RolesConst.ROLE_ADMIN)
            return true;
        return role_A < role_B;
    }

}
