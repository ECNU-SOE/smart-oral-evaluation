package net.ecnu.service;

import net.ecnu.controller.request.UserRegisterReq;

public interface UserService {

    Object register(UserRegisterReq userRegisterReq);
}
