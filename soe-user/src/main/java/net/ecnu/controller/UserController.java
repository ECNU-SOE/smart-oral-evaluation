package net.ecnu.controller;

import com.alibaba.fastjson.JSONObject;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Find;
import net.ecnu.controller.request.UserReq;
import net.ecnu.model.UserDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.service.UserService;
import net.ecnu.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;


@RestController
@RequestMapping("/api/user/v1")
@CrossOrigin //sendSms跨域支持
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("register")
    public JsonData register(@RequestBody @Validated(Create.class) UserReq userReq) {
        Object data = userService.register(userReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 用户登录
     */
    @PostMapping("login")
    public JsonData login(@RequestBody @Validated(Find.class) UserReq userReq) {
        Object data = userService.login(userReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("info")
    public JsonData info(HttpServletRequest req){
        Object data = userService.info(req);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("update")
    public JsonData update(@RequestBody UserDO user){
        int num = userService.update(user);
        if (num>=1){
            return JsonData.buildSuccess();
        }
        return JsonData.buildError("用户信息更新失败");
    }

    @GetMapping("/send/{phone}")
    public JsonData send(@PathVariable("phone") String phone){
        HashMap<String,Object> map = new HashMap<>();
        map.put("code",1234);
        final String templateCode = "SMS_262610596"; //阿里云短信模板,需要向阿里云申请
        boolean isSend = userService.send(phone,templateCode, map);
        if (isSend)
            return JsonData.buildSuccess();
        else
            return JsonData.buildError("短信发送错误");
    }

}
