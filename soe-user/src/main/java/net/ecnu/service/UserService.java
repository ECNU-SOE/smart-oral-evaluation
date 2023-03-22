package net.ecnu.service;

import net.ecnu.controller.request.UserReq;
import net.ecnu.model.UserDO;
import net.ecnu.model.common.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {

    Object register(UserReq userReq);

    Object login(UserReq userReq);

    int update(UserDO user);

    boolean send(String phoneNum);

    Object getUserInfo();

}
