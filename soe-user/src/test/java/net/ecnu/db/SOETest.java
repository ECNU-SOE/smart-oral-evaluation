package net.ecnu.db;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.UserApplication;
import net.ecnu.mapper.ClassMapper;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.mapper.SignLogMapper;
import net.ecnu.mapper.SignMapper;
import net.ecnu.model.ClassDO;
import net.ecnu.model.CourseDO;
import net.ecnu.model.SignLogDO;
import net.ecnu.model.authentication.SysApiNode;
import net.ecnu.service.authentication.SysApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class SOETest {

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysApiService sysApiService;

    @Resource
    private SignMapper signMapper;

    @Resource
    private SignLogMapper signLogMapper;

    @Test
    public void pinyinTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2009-12-31");
        Date date2 = sdf.parse("2019-01-31");
    }

    @Test
    public void passwordEncodeTest(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        //$2a$10$pyi3kjKpjNIqZ1AWccffqOHV9DIC/tFnlbN9QsCwhb3q8OXZkU3FS

    }

    @Test
    public void getApiTreeTest(){
        List<SysApiNode> apiTree = sysApiService.getApiTree("", false);
        log.info("apiTree = {}", JSON.toJSON(apiTree));
    }

    private Date getLatestTime(List<Date> dateList){
        if (dateList.size()==0)
            return null;
        Date latest = dateList.get(0);
        for (int i = 1;i<dateList.size();i++)
            if (latest.before(dateList.get(i)))
                latest = dateList.get(i);
        return latest;
    }
}
