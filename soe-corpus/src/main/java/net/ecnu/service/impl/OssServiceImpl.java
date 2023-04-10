package net.ecnu.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.model.common.LoginUser;
import net.ecnu.service.OssService;
import net.ecnu.util.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class OssServiceImpl implements OssService {

//    @Value("${aliyun.soe.endpoint}")
//    private String endpoint;
//
//    @Value("${aliyun.soe.access-key-id}")
//    private String accessKeyId;
//
//    @Value("${aliyun.soe.access-key-secret}")
//    private String accessKeySecret;
//
//    @Value("${aliyun.soe.bucket-name}")
//    private String bucketName;

    @Override
    public Object uploadAudio(MultipartFile file) {
        String endpoint = "oss-cn-shanghai.aliyuncs.com";
        String accessKeyId = "LTAI5tEnQGwgsUNxNdqRyS5v";
        String accessKeySecret = "H9umfcIvCy83WwoaUPKADQs0LIHcyw";
        String bucketName = "soe-oss";

        //登录用户 且 有权限才能 上传文件
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) {
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        }

        //创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //处理生成文件名newFilename  : corpus/2022/10/12/uuid/
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String folderPath = dateTimeFormatter.format(LocalDateTime.now());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFilename = "corpus/" + folderPath + "/" + CommonUtil.generateUUID() + "." + extension;

        //ossClient上传操作
        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, newFilename, file.getInputStream());
            if (putObjectResult != null) {
                return "https://" + bucketName + "." + endpoint + "/" + newFilename;
            }
        } catch (IOException e) {
            log.error("文件上传失败:{}", e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return null;
    }

}
