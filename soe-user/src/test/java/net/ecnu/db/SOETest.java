package net.ecnu.db;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.UserApplication;
import net.ecnu.mapper.UserRoleMapper;
import net.ecnu.model.UserRoleDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class SOETest {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    public void userTest(){
        Integer role = 2;
        switch (role){
            case 1:
                System.out.println("你");
                break;
            case 2:
                System.out.println("你好");
                break;
            case 3:
                System.out.println("你好呀");
        }
    }
}
