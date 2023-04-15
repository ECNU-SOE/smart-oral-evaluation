package net.ecnu.manager;

import net.ecnu.model.UserClassDO;

public interface UserClassManager {
    UserClassDO getByAccountNoAndClassId(String accountNo, String courseId);
//    List<UserClassDO> pageByFilter(UsrClassFilterReq usrClassFilterReq, PageData pageData);
//    int countByFilter(UsrClassFilterReq usrClassFilterReq);
}
