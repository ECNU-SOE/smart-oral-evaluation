package net.ecnu.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.service.OssService;
import net.ecnu.util.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Override
    public Object uploadAudio(MultipartFile file) {
        String endpoint = "oss-cn-shanghai.aliyuncs.com";
        String accessKeyId = "LTAI5tEnQGwgsUNxNdqRyS5v";
        String accessKeySecret = "H9umfcIvCy83WwoaUPKADQs0LIHcyw";
        String bucketName = "soe-oss";

        //创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //处理生成文件名newFilename  : corpus/2022/10/12/sdsdwe/
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
