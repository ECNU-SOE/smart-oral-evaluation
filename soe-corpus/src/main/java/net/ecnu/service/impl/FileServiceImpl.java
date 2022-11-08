package net.ecnu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import com.tencentcloudapi.soe.v20180724.models.WordRsp;
import net.ecnu.controller.Result;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.service.FileService;
import net.ecnu.util.SOEFileUtil;
import net.ecnu.util.SOEWordUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private CpsrcdManager cpsrcdManager;

    @Override
    public Result evaluate(MultipartFile audio,String text,String pinyin,String mode) {
        Result result = new Result();
        try {

            File file = SOEFileUtil.MultipartFile2File(audio);

            int PKG_SIZE = 2 * 1024; //分片大小
            String secretId = "AKIDZNBuVGvNrnbJoBYv9gr3EQWUJ8w1DPWS";
            String secretKey = "YxBE6oIZgh7OZYM2Zv7SghigV96g6LNw";
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
            Credential cred = new Credential(secretId, secretKey);

            // 实例化要请求产品的client对象,clientProfile是可选的
            SoeClient client = new SoeClient(cred, "");
            String sessionId = UUID.randomUUID().toString();


            TransmitOralProcessWithInitRequest req = new TransmitOralProcessWithInitRequest();

            text.trim();
            req.setVoiceEncodeType(1L);  //语音数据类型1:pcm
            req.setVoiceFileType(3L); //语音文件类型1: raw，2: wav，3: mp3，4: speex
            req.setSessionId(sessionId); //唯一标识
            if(pinyin.isEmpty()){//普通评测模式
                req.setRefText(text);
                req.setTextMode(0L); //文本格式.0普通文本 1,音素结构
            }else{//指定拼音评测模式
                String s = SOEWordUtil.formatText(text,pinyin);
                req.setRefText(s); //文本
                req.setTextMode(1L); //文本格式.0普通文本 1,音素结构
            }
            req.setWorkMode(0L); //0,流式分片,1一次性评测
            if("0".equals(mode)){
                req.setEvalMode(0L); //评估模式,0,单词.1,句子,2,段落,3自由说,4单词纠错
            }else if("1".equals(mode)){
                req.setEvalMode(1L);
            }else if("2".equals(mode)){
                req.setEvalMode(2L);
            }else if(mode.isEmpty()){
                return null;
            }else{
                return null;
            }
            req.setScoreCoeff(1.0f); //评估难度
            req.setServerType(1L); //服务类型.0英文,1中文
            req.setIsAsync(0L); //异步
            req.setIsQuery(0L); //轮询

            TransmitOralProcessWithInitResponse resp = null;

            //将文件装换成base64
            //byte[] data = Files.readAllBytes(Paths.get(file));
            byte[] data = SOEFileUtil.File2byte(file);
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

                result.setSuggestedScore(resp.getSuggestedScore().toString());
                result.setPronAccuracy(resp.getPronAccuracy().toString());
                result.setPronFluency(resp.getPronFluency().toString());
                result.setPronCompletion(resp.getPronCompletion().toString());

                WordRsp[] words1 = resp.getWords();

                //将所有得分不超过85分的汉字加入返回集合
                List<JSONObject> words = new ArrayList<>();
                for(int k = 0; k< words1.length; k++){
                        if(words1[k].getPronAccuracy()<85|| words1[k].getPronFluency()<0.85)
                            if(!"*".equals(words1[k].getWord())){
                                JSONObject temp_json = new JSONObject();
                                temp_json.put("word", words1[k].getWord().toString());
                                temp_json.put("PronAccuracy", words1[k].getPronAccuracy().toString());
                                temp_json.put("PronFluency", words1[k].getPronFluency().toString());
                                words.add(temp_json);
                            }
                }
                result.setWords(words);


                // 输出json格式的字符串回包
                System.out.println(TransmitOralProcessWithInitResponse.toJsonString(resp));
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<JSONObject> getCorpusesByGroupId(String cpsgrpId) {
        List<CpsrcdDO> corpuses = cpsrcdManager.getCorpusesByGroupId(cpsgrpId);
        List<JSONObject> list = new ArrayList<>();
        for(int i=0;i<=5;i++){
            JSONObject temp = new JSONObject();
            temp.put("type",i);
            temp.put("word",null);
            temp.put("corpus_id",null);
            list.add(temp);
        }
        for(int i=0;i<corpuses.size();i++){
            JSONObject o = new JSONObject();
            for(int j=0;j<=5;j++){
                if(corpuses.get(i).getType()==j){
                    if(list.get(j).get("word")==null){
                        List<String> list_word = new ArrayList<>();
                        list_word.add(corpuses.get(i).getRefText());
                        list.get(j).put("word",list_word);
                        List<String> list_id = new ArrayList<>();
                        list_id.add(corpuses.get(i).getId());
                        list.get(j).put("corpus_id",list_id);
                    }else{
                        List<String> temp_word = (List<String>) list.get(j).get("word");
                        temp_word.add(corpuses.get(i).getRefText());
                        list.get(j).replace("word",temp_word);
                        List<String> temp_id = (List<String>)list.get(j).get("corpus_id");
                        temp_id.add(corpuses.get(i).getId());
                        list.get(j).replace("corpus_id",temp_id);
                    }

                }
            }
        }
        System.out.println("list 是: " + list);
        return list;
    }
}
