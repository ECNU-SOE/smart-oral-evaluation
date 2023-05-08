package net.ecnu.manager;

import net.ecnu.controller.request.UsrClassFilterReq;
import net.ecnu.model.UserClassDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface UserClassManager {
    UserClassDO getByAccountNoAndClassId(String accountNo, String courseId);
    List<UserClassDO> pageByfilter(UsrClassFilterReq usrClassFilter, PageData pageData);
    int countByFilter(UsrClassFilterReq usrClassFilter);
}
