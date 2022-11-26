package net.ecnu.db;


import lombok.extern.slf4j.Slf4j;
import net.ecnu.UserApplication;
import net.ecnu.controller.request.UserReq;
import net.ecnu.service.UserService;
import net.ecnu.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class SOETest {

    @Autowired
    UserService userService;

    @Test
    public void userTest(){
        UserReq userReq = new UserReq();
        userReq.setPhone("18786978272");
        userReq.setCode("123456");
        userReq.setPwd("123456");
        Object info = userService.info(userReq);
        String s = JsonUtil.obj2Json(info);
        System.out.println("s = " + s);
    }
}
