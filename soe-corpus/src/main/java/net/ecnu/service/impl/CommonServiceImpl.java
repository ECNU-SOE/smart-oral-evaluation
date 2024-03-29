package net.ecnu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.service.CommonService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Override
    public Object listMotherTongue() {
        //            File file = ResourceUtils.getFile("classpath:static/MotherTongueList.json");
//            ClassPathResource classPathResource = new ClassPathResource("static/MotherTongueList.json");
//            File file = classPathResource.getFile();
//            String content = FileUtils.readFileToString(file, "UTF-8");
        String content = "[{\"id\":1,\"motherTongue\":\"汉语\"},{\"id\":2,\"motherTongue\":\"英语\"},{\"id\":3,\"motherTongue\":\"俄语\"},{\"id\":4,\"motherTongue\":\"德语\"},{\"id\":5,\"motherTongue\":\"法语\"}]";
        List<Object> languages = JSON.parseArray(content);
        //处理返回结果
        Map<String, Object> result = new HashMap<>(2);
        result.put("size", languages.size());
        result.put("languages", languages);
        return result;
    }

    @Override
    public Object listMotherTongue2() throws IOException {
        /*File file = ResourceUtils.getFile("classpath:static/MotherTongueList.json");
        ClassPathResource classPathResource = new ClassPathResource("static/MotherTongueList.json");
        File file = classPathResource.getFile();
        tring content = FileUtils.readFileToString(file, "UTF-8");*/

        Resource resource = new ClassPathResource("static/MotherTongueList.json");
        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder stringBuilder = new StringBuilder();
        String data = null;
        while ((data = br.readLine()) != null) {
            stringBuilder.append(data);
        }
        br.close();
        isr.close();
        is.close();
        JSONArray jsonArray = JSONArray.parseArray(stringBuilder.toString());
        //String content = "[{\"id\":1,\"motherTongue\":\"汉语\"},{\"id\":2,\"motherTongue\":\"英语\"},{\"id\":3,\"motherTongue\":\"俄语\"},{\"id\":4,\"motherTongue\":\"德语\"},{\"id\":5,\"motherTongue\":\"法语\"}]";
        //List<Object> languages = JSON.parseArray(content);
        //处理返回结果
        Map<String, Object> result = new HashMap<>(2);
        result.put("size", jsonArray.size());
        result.put("languages", jsonArray);
        return result;
    }

    @Override
    public Object getCpsgrpIdByFirstLanguageId(int languageId) {
        return "cpsgrp_1588871928125460480";
    }
}
