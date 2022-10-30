package net.ecnu.service;

import net.ecnu.controller.request.UserReq;

public interface UserService {

    Object register(UserReq userReq);

    Object login(UserReq userReq);
}
