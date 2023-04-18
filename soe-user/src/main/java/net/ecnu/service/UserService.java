package net.ecnu.service;

import net.ecnu.controller.request.UserFilterReq;
import net.ecnu.controller.request.UserReq;
import net.ecnu.model.UserDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {

    Object register(UserReq userReq);

    Object login(UserReq userReq);

    int update(UserDO user);

    boolean send(String phoneNum);

    Object getUserInfo();
    Object pageByFilter(UserFilterReq userFilterReq, PageData pageData);

}
