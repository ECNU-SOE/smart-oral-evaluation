package net.ecnu.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.service.FileService;
import net.ecnu.util.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public Object uploadVoiceRecord(MultipartFile file) {
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

    @Override
    public void evaluate() {
        try {
            String file = ""; //本地音频文件
            int PKG_SIZE = 2 * 1024; //分片大小
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
            Credential cred = new Credential("", "");

            // 实例化要请求产品的client对象,clientProfile是可选的
            SoeClient client = new SoeClient(cred, "");
            String sessionId = UUID.randomUUID().toString();


            TransmitOralProcessWithInitRequest req = new TransmitOralProcessWithInitRequest();


            req.setVoiceEncodeType(1L);  //语音数据类型1:pcm
            req.setVoiceFileType(3L); //语音文件类型
            req.setSessionId(sessionId); //唯一标识
            req.setRefText("book"); //文本
            req.setWorkMode(0L); //0,流式分片,1一次性评测
            req.setEvalMode(1L); //评估模式,0,单词.1,句子,2,段落,3自由说,4单词纠错
            req.setScoreCoeff(1.0f); //评估难度
            req.setServerType(1L); //服务类型.0英文,1中文
            req.setIsAsync(0L); //异步
            req.setIsQuery(0L); //轮询
            req.setTextMode(0L); //文本格式.0普通文本 1,音素结构


            //将文件装换成base64
            byte[] data = Files.readAllBytes(Paths.get(file));
            int pkgNum = (int) Math.ceil((double) data.length / PKG_SIZE);
            for (int i = 1; i <= pkgNum; i++) {
                int lastIndex = i * PKG_SIZE;
                if (i == pkgNum) {
                    lastIndex = data.length;
                }
                byte[] buf = Arrays.copyOfRange(data, (i - 1) * PKG_SIZE, lastIndex);
//                String base64Str = new sun.misc.BASE64Encoder().encode(buf);
                String base64Str = Base64.getEncoder().encodeToString(buf);
                req.setUserVoiceData(base64Str);
                req.setSeqId((long) i);
                if (i == pkgNum) {
                    req.setIsEnd(1L);
                } else {
                    req.setIsEnd(0L);
                }

                TransmitOralProcessWithInitResponse resp = client.TransmitOralProcessWithInit(req);

                // 输出json格式的字符串回包
                System.out.println(TransmitOralProcessWithInitResponse.toJsonString(resp));
                // 也可以取出单个值。
                // 你可以通过官网接口文档或跳转到response对象的定义处查看返回字段的定义
                System.out.println(resp.getPronAccuracy());
            }
        } catch (TencentCloudSDKException | IOException e) {
            e.printStackTrace();
        }
    }
}
