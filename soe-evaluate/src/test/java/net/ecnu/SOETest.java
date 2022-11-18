package net.ecnu;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.domain.Cpsgrp;
import net.ecnu.domain.Cpsrcd;
import net.ecnu.mapper.CpsgrpDao;
import net.ecnu.mapper.CpsrcdDao;
import net.ecnu.service.FileService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EvaluateApplication.class)
@Slf4j
public class SOETest {

    @Autowired
    private FileService fileService;
    @Test
    public void testTGX(){
        String s ="cpsgrp_1588871928125460480";
        JSONObject json = fileService.getCorpusesByGroupId(s);
        System.out.println("json = " + json);
    }

    @Test
    public void testRegu() throws JSONException {
        String reg = "^[A-Za-z]{1,10}[1-4]{1}$";
        String str = "tian";
        boolean flag = str.matches(reg);
        System.out.println(flag);
    }
}
