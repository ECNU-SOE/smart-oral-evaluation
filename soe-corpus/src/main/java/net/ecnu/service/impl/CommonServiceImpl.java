package net.ecnu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.service.CommonService;
import net.ecnu.service.FileService;
import net.ecnu.util.CommonUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}
