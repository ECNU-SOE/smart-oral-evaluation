package net.ecnu.controller;

import net.ecnu.controller.request.UserRegisterReq;
import net.ecnu.service.UserService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("register")
    public JsonData register(@RequestBody @Validated UserRegisterReq userRegisterReq) {
        Object data = userService.register(userRegisterReq);
        return JsonData.buildSuccess(data);
    }
}
