package net.ecnu.biz;


import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.CorpusApplication;
import net.ecnu.service.EvaluateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CorpusApplication.class)
@Slf4j
public class SOETest {

    @Autowired
    private EvaluateService evaluateService;

    @Value(value = "${aliyun.oss.endpoint}")
    private String ossEndpoint;


    String sb = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "  <xml_result>\n" +
            "      <read_sentence lan=\"cn\" type=\"study\" version=\"7,0,0,1024\">\n" +
            "          <rec_paper>\n" +
            "              <read_sentence accuracy_score=\"0.000000\" beg_pos=\"0\" content=\"今天天气怎么样。\" emotion_score=\"0.000000\" end_pos=\"150\" except_info=\"0\" fluency_score=\"86.369194\" integrity_score=\"100.000000\" is_rejected=\"false\" phone_score=\"85.714287\" time_len=\"150\" tone_score=\"100.000000\" total_score=\"85.375130\">\n" +
            "                  <sentence beg_pos=\"0\" content=\"今天天气怎么样\" end_pos=\"150\" fluency_score=\"0.000000\" phone_score=\"85.714287\" time_len=\"150\" tone_score=\"100.000000\" total_score=\"80.840004\">\n" +
            "                      <word beg_pos=\"0\" content=\"今\" end_pos=\"24\" symbol=\"jin1\" time_len=\"24\">\n" +
            "                          <syll beg_pos=\"0\" content=\"fil\" dp_message=\"32\" end_pos=\"1\" rec_node_type=\"fil\" time_len=\"1\">\n" +
            "                              <phone beg_pos=\"0\" content=\"fil\" dp_message=\"32\" end_pos=\"1\" rec_node_type=\"fil\" time_len=\"1\"></phone>\n" +
            "                          </syll>\n" +
            "                          <syll beg_pos=\"1\" content=\"今\" dp_message=\"0\" end_pos=\"24\" rec_node_type=\"paper\" symbol=\"jin1\" time_len=\"23\">\n" +
            "                              <phone beg_pos=\"1\" content=\"j\" dp_message=\"0\" end_pos=\"4\" is_yun=\"0\" perr_level_msg=\"3\" perr_msg=\"1\" rec_node_type=\"paper\" time_len=\"3\"></phone>\n" +
            "                              <phone beg_pos=\"4\" content=\"in\" dp_message=\"0\" end_pos=\"24\" is_yun=\"1\" mono_tone=\"TONE1\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"20\"></phone>\n" +
            "                          </syll>\n" +
            "                      </word>\n" +
            "                      <word beg_pos=\"24\" content=\"天\" end_pos=\"40\" symbol=\"tian1\" time_len=\"16\">\n" +
            "                          <syll beg_pos=\"24\" content=\"天\" dp_message=\"0\" end_pos=\"40\" rec_node_type=\"paper\" symbol=\"tian1\" time_len=\"16\">\n" +
            "                              <phone beg_pos=\"24\" content=\"t\" dp_message=\"0\" end_pos=\"29\" is_yun=\"0\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"5\"></phone>\n" +
            "                              <phone beg_pos=\"29\" content=\"ian\" dp_message=\"0\" end_pos=\"40\" is_yun=\"1\" mono_tone=\"TONE1\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"11\"></phone>\n" +
            "                          </syll>\n" +
            "                      </word>\n" +
            "                      <word beg_pos=\"40\" content=\"天\" end_pos=\"58\" symbol=\"tian1\" time_len=\"18\">\n" +
            "                          <syll beg_pos=\"40\" content=\"天\" dp_message=\"0\" end_pos=\"58\" rec_node_type=\"paper\" symbol=\"tian1\" time_len=\"18\">\n" +
            "                              <phone beg_pos=\"40\" content=\"t\" dp_message=\"0\" end_pos=\"46\" is_yun=\"0\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"6\"></phone>\n" +
            "                              <phone beg_pos=\"46\" content=\"ian\" dp_message=\"0\" end_pos=\"58\" is_yun=\"1\" mono_tone=\"TONE1\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"12\"></phone>\n" +
            "                          </syll>\n" +
            "                      </word>\n" +
            "                      <word beg_pos=\"58\" content=\"气\" end_pos=\"74\" symbol=\"qi9\" time_len=\"16\">\n" +
            "                          <syll beg_pos=\"58\" content=\"气\" dp_message=\"0\" end_pos=\"74\" rec_node_type=\"paper\" symbol=\"qi0\" time_len=\"16\">\n" +
            "                              <phone beg_pos=\"58\" content=\"q\" dp_message=\"0\" end_pos=\"66\" is_yun=\"0\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"8\"></phone>\n" +
            "                              <phone beg_pos=\"66\" content=\"i\" dp_message=\"0\" end_pos=\"74\" is_yun=\"1\" mono_tone=\"TONE0\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"8\"></phone>\n" +
            "                          </syll>\n" +
            "                      </word>\n" +
            "                      <word beg_pos=\"74\" content=\"怎\" end_pos=\"85\" symbol=\"zen3\" time_len=\"11\">\n" +
            "                          <syll beg_pos=\"74\" content=\"怎\" dp_message=\"0\" end_pos=\"85\" rec_node_type=\"paper\" symbol=\"zen3\" time_len=\"11\">\n" +
            "                              <phone beg_pos=\"74\" content=\"z\" dp_message=\"0\" end_pos=\"80\" is_yun=\"0\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"6\"></phone>\n" +
            "                              <phone beg_pos=\"80\" content=\"en\" dp_message=\"0\" end_pos=\"85\" is_yun=\"1\" mono_tone=\"TONE3\" perr_level_msg=\"3\" perr_msg=\"1\" rec_node_type=\"paper\" time_len=\"5\"></phone>\n" +
            "                          </syll>\n" +
            "                      </word>\n" +
            "                      <word beg_pos=\"85\" content=\"么\" end_pos=\"93\" symbol=\"me5\" time_len=\"8\">\n" +
            "                          <syll beg_pos=\"85\" content=\"么\" dp_message=\"0\" end_pos=\"93\" rec_node_type=\"paper\" symbol=\"me0\" time_len=\"8\">\n" +
            "                              <phone beg_pos=\"85\" content=\"m\" dp_message=\"0\" end_pos=\"88\" is_yun=\"0\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"3\"></phone>\n" +
            "                              <phone beg_pos=\"88\" content=\"e\" dp_message=\"0\" end_pos=\"93\" is_yun=\"1\" mono_tone=\"TONE0\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"5\"></phone>\n" +
            "                          </syll>\n" +
            "                      </word>\n" +
            "                      <word beg_pos=\"93\" content=\"样\" end_pos=\"150\" symbol=\"yang4\" time_len=\"57\">\n" +
            "                          <syll beg_pos=\"93\" content=\"样\" dp_message=\"0\" end_pos=\"112\" rec_node_type=\"paper\" symbol=\"yang4\" time_len=\"19\">\n" +
            "                              <phone beg_pos=\"93\" content=\"_i\" dp_message=\"0\" end_pos=\"96\" is_yun=\"0\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"3\"></phone>\n" +
            "                              <phone beg_pos=\"96\" content=\"iang\" dp_message=\"0\" end_pos=\"112\" is_yun=\"1\" mono_tone=\"TONE4\" perr_level_msg=\"1\" perr_msg=\"0\" rec_node_type=\"paper\" time_len=\"16\"></phone>\n" +
            "                          </syll>\n" +
            "                          <syll beg_pos=\"112\" content=\"sil\" dp_message=\"0\" end_pos=\"150\" rec_node_type=\"sil\" time_len=\"38\">\n" +
            "                              <phone beg_pos=\"112\" content=\"sil\" end_pos=\"150\" time_len=\"38\"></phone>\n" +
            "                          </syll>\n" +
            "                      </word>\n" +
            "                  </sentence>\n" +
            "              </read_sentence>\n" +
            "          </rec_paper>\n" +
            "      </read_sentence>\n" +
            "  </xml_result>";


    @Test
    public void evaluateTest() {
        try {
            String file = "C:\\Users\\Lenovo\\Desktop\\smart-oral-evaluation-main\\soe-corpus\\src\\main\\resources\\read_sentence_cn.mp3"; //本地音频文件
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

    @Test
    public void IDTest() {
        System.out.println(ossEndpoint);
    }


    @Test
    public void xmlToJson() {
//        JSONObject xmlJSONObj = XML.toJSONObject(sb.replace("<xml>", "").replace("</xml>", ""));
        JSONObject xmlJSONObj = XML.toJSONObject(sb);
        System.out.println(xmlJSONObj.toString());
//        Map<String, Object> stringObjectMap = XmlUtil.xmlToMap(sb.toString());
//        String json = JSONObject.toJSONString(stringObjectMap);
//        System.out.println(json);
//        JSONObject jsonObject = JSONObject.parseObject(json, JSONObject.class);
//        System.out.println(jsonObject.toJSONString());
    }
}
