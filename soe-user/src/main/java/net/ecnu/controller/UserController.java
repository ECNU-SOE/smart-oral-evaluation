package net.ecnu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Find;
import net.ecnu.controller.request.UserReq;
import net.ecnu.model.UserDO;
import net.ecnu.service.UserService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "端上用户管理")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @GetMapping("test")
    public JsonData test() {
        return JsonData.buildSuccess("data");
    }


    /**
     * 用户注册
     */
    @ApiOperation("用户注册")
    @PostMapping("register")
    public JsonData register(@RequestBody @Validated(Create.class) UserReq userReq) {
        Object data = userService.register(userReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 用户登录
     */
    @ApiOperation("用户登录")
    @PostMapping("login")
    public JsonData login(@RequestBody @Validated(Find.class) UserReq userReq) {
        Object data = userService.login(userReq);
        return JsonData.buildSuccess(data);
    }

    @ApiOperation("获取用户信息")
    /**
     * 查询用户详情
     */
    @PostMapping("info")
    public JsonData info(HttpServletRequest req) {
        Object data = userService.info(req);
        if (data == null)
            return JsonData.buildError("查询用户信息错误");
        return JsonData.buildSuccess(data);
    }

    /**
     * 查询登陆用户详情（需要携带token）
     */
    @GetMapping("info2")
    public JsonData info2() {
        Object data = userService.getUserInfo();
        return JsonData.buildSuccess(data);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("update")
    public JsonData update(@RequestBody UserDO user) {
        int num = userService.update(user);
        if (num >= 1) {
            return JsonData.buildSuccess();
        }
        return JsonData.buildError("用户信息更新失败");
    }

    @ApiOperation("发送短信验证码")
    @GetMapping("/send/{phone}")
    public JsonData send(@PathVariable("phone") String phone) {
        boolean isSend = userService.send(phone);
        if (isSend)
            return JsonData.buildSuccess();
        else
            return JsonData.buildError("短信发送错误");
    }

    @PostMapping("create")
    public JsonData create(){

        return null;
    }
}
