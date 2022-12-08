package net.ecnu.manager;


import net.ecnu.model.UserDO;

import java.util.List;

public interface UserManager {

    UserDO selectOneByPhone(String phone);

    List<UserDO> selectAllByPhone(String phone);
}
