package net.ecnu.service;

import net.ecnu.controller.request.UserReq;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    Object register(UserReq userReq);

    Object login(UserReq userReq);

    Object info(HttpServletRequest req);
}
