package net.ecnu.db;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.UserApplication;
import net.ecnu.mapper.ClassMapper;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.model.ClassDO;
import net.ecnu.model.CourseDO;
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
    private ClassMapper classMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void pinyinTest() {
        CourseDO courseDO = courseMapper.selectById("course_1645337883818725376");
        List<ClassDO> classDOS = classMapper.selectList(new QueryWrapper<ClassDO>()
                .eq("course_id", courseDO.getId())
        );
        if (classDOS.size()!=0)
            System.out.println("不为空");
        else
            System.out.println("为空");
    }
}
