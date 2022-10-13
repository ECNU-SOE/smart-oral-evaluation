package net.ecnu.biz;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.CorpusApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CorpusApplication.class)
@Slf4j
public class SOETest {

    @Test
    public void evaluateTest(){
        try {
            String file = "/Users/lyw/projects/smart-oral-evaluation/soe-corpus/src/main/resources/read_sentence_cn.mp3"; //本地音频文件
            int PKG_SIZE = 2 * 1024; //分片大小
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
            String appid = "1301114428";
            String secretId = "AKIDiIhssjOoGe2UwLZ92wjN2XqcFQQEpDOT";
            String secretKey = "whJj06yc3PhpnZBdu9GB0f9RZZaZTEsO";
            Credential cred = new Credential(secretId, secretKey);

            // 实例化要请求产品的client对象,clientProfile是可选的
            SoeClient client = new SoeClient(cred, "");
            String sessionId = UUID.randomUUID().toString();


            TransmitOralProcessWithInitRequest req = new TransmitOralProcessWithInitRequest();


            req.setVoiceEncodeType(1L);  //语音数据类型1：pcm
            req.setVoiceFileType(3L); //语音文件类型1: raw，2: wav，3: mp3，4: speex
            req.setSessionId(sessionId); //唯一标识
            req.setRefText("今天天气怎么样"); //文本
            req.setWorkMode(0L); //0,流式分片,1一次性评测
            req.setEvalMode(1L); //评估模式,0,单词.1,句子,2,段落,3自由说,4单词纠错
            req.setScoreCoeff(1.0f); //评估难度
            req.setServerType(1L); //服务类型.0英文,1中文
            req.setIsAsync(0L); //异步
            req.setIsQuery(0L); //轮询
            req.setTextMode(0L); //文本格式.0普通文本 1,音素结构

            TransmitOralProcessWithInitResponse resp = null;
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
                resp = client.TransmitOralProcessWithInit(req);
            }
            // 输出json格式的字符串回包
            System.out.println(TransmitOralProcessWithInitResponse.toJsonString(resp));
            // 也可以取出单个值。
            // 你可以通过官网接口文档或跳转到response对象的定义处查看返回字段的定义
            System.out.println(resp.getPronAccuracy());
        } catch (TencentCloudSDKException | IOException e) {
            e.printStackTrace();
        }

    }
}
