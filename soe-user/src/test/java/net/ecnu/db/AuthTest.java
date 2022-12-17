package net.ecnu.db;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.UserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @Author 刘少煜
 * @Date 2022/12/11 14:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class AuthTest {
    //$2a$10$jpvRrMYykpBv1.w9snhxC.1.cxoNu/93FO6JIIKSo3WI/oRFFDD8C
    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void passwordTest(){
        String password = "abcd1234";
        log.info("初始密码加密：{}", JSON.toJSON(passwordEncoder.encode(password)));
    }

    @Test
    public void apiIdGenerate(){
        List<Integer> list = new ArrayList<>();
        for(int i=18;i<=79;i++){
            list.add(i);
        }
        String collect = list.stream().map(e -> String.valueOf(e)).collect(Collectors.joining(","));
        System.out.println("collect = " + collect);
    }
}
