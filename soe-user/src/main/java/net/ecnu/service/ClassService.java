package net.ecnu.service;

import net.ecnu.controller.request.*;
import net.ecnu.model.ClassDO;
import com.baomidou.mybatisplus.extension.service.IService;
import net.ecnu.model.common.PageData;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author TGX
 * @since 2023-04-10
 */
public interface ClassService extends IService<ClassDO> {

    Object add(ClassAddReq classAddReq);

    Object del(String id);

    Object update(ClassUpdateReq classUpdateReq);

    Object pageByFilter(ClassFilterReq classFilter, PageData pageData);

    Object addUsrClass(UsrClassReq usrClassReq);
    Object addUsrClassBatch(UsrClassAddBatchReq usrClassAddBatchReq);

    Object delUsrClass(String id);
    Object addTest(TestAddReq testAddReq);
    Object addCpsgrp(ClassCpsgrpReq classCpsgrpReq);

    Object delTest(String id);

    Object updateTest(TestUpdateReq testUpdateReq);

    Object detail(String classId);

    /**
     * 查询用户选课列表
     */
    Object listSel(String accountNo);
    //查看班级成员信息
    Object listMem(UsrClassFilterReq usrClassFilter,PageData pageData);

}
