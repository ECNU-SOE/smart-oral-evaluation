package net.ecnu.service;

import net.ecnu.controller.request.*;
import net.ecnu.model.ClassDO;
import com.baomidou.mybatisplus.extension.service.IService;
import net.ecnu.model.UserClassDO;
import net.ecnu.model.common.PageData;

/**
 * <p>
 *  服务类
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
    Object addUsrClass(UsrClassAddReq usrClassAddReq);
    Object delUsrClass(String id);
    Object listUsrClass(UserClassDO userClassDO);
    Object addTest(TestAddReq testAddReq);
    Object delTest(String id);
    Object updateTest(TestUpdateReq testUpdateReq);
}