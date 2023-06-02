package net.ecnu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Find;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.SignReq;
import net.ecnu.controller.request.UserFilterReq;
import net.ecnu.controller.request.UserReq;
import net.ecnu.model.common.PageData;
import net.ecnu.service.UserService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Api(value = "端上用户管理")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @ApiOperation("用户注册")
    @PostMapping("register")
    public JsonData register(@RequestBody @Validated(Create.class) UserReq userReq) {
        Object data = userService.register(userReq);
        return JsonData.buildSuccess(data);
    }

    @ApiOperation("删除用户")
    @GetMapping("del")
    public JsonData register(@RequestParam String accountNo) {
        Object data = userService.del(accountNo);
        return JsonData.buildSuccess(data);
    }

    @ApiOperation("批量新增用户")
    @PostMapping("batch")
    public JsonData batch(@RequestParam("file") MultipartFile excelFile) throws IOException {
        Object data = userService.batch(excelFile);
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

    /**
     * 查询登陆用户详情（需要携带token）
     */
    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public JsonData info() {
        Object data = userService.getUserInfo();
        return JsonData.buildSuccess(data);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("update")
    public JsonData update(@RequestBody @Validated(Update.class) UserReq userReq) {
        Object data = userService.update(userReq);
        return JsonData.buildSuccess(data);
    }

    @ApiOperation("查询user列表")
    @PostMapping("list")
    public JsonData list(@RequestParam(value = "cur", defaultValue = "1") int cur,
                         @RequestParam(value = "size", defaultValue = "50") int size,
                         @RequestBody UserFilterReq userFilterReq) {
        Object data = userService.pageByFilter(userFilterReq, new PageData(cur, size));
        return JsonData.buildSuccess(data);
    }

    @ApiOperation("查询info列表")
    @PostMapping("info_list")
    public JsonData info_list(@RequestParam("account_list") List<String> accountList) {
        Object data = userService.getInfoList(accountList);
        return JsonData.buildSuccess(data);
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

    @ApiOperation("用户签到")
    @GetMapping("sign")
    public JsonData sign() {
        Object data = userService.sign();
        return JsonData.buildSuccess(data);
    }

    @ApiOperation("用户补签")
    @PostMapping("resign")
    public JsonData resign(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate resignDate){
        Object data = userService.resign(resignDate);
        return JsonData.buildSuccess(data);
    }

    @ApiOperation("获取签到信息")
    @GetMapping("sign_info")
    public JsonData signInfo(@RequestParam(value = "month",required = true) Integer month){
        Object data = userService.signInfo(month);
        return JsonData.buildSuccess(data);
    }

}
