package net.ecnu.manager;


import net.ecnu.controller.request.UserFilterReq;
import net.ecnu.model.UserDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface UserManager {

    UserDO selectOneByPhone(String phone);

    List<UserDO> selectAllByPhone(String phone);

    UserDO selectOneByAccountNo(String accountNo);
    List<UserDO> pageByFilter(UserFilterReq userFilterReq, PageData pageData);
    int countByFilter(UserFilterReq userFilterReq);
}
