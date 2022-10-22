package net.ecnu.manager;


import net.ecnu.model.UserDO;

public interface UserManager {

    UserDO selectOneByPhone(String phone);
}
