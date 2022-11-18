package net.ecnu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import com.tencentcloudapi.soe.v20180724.models.WordRsp;
import net.ecnu.controller.Result;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CorpusMapper;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.model.CpsgrpDO;
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

    @Autowired
   private CpsgrpMapper cpsgrpMapper;



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
            }else if("1".equals(mode)||"2".equals(mode)){
                req.setEvalMode(1L);
            }else if("3".equals(mode)||"5".equals(mode)){
                req.setEvalMode(2L);
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
                int wrong_words =0;
                //将所有得分不超过85分的汉字加入返回集合
                List<JSONObject> words = new ArrayList<>();
                if ("Finished".equals(resp.getStatus())){
                    for(int k = 0; k< words1.length; k++){
                        if(words1[k].getPronAccuracy()<90|| words1[k].getPronFluency()<0.90)
                            if(!"*".equals(words1[k].getWord())){
                                wrong_words++;//统计错字字数
                                JSONObject temp_json = new JSONObject();
                                temp_json.put("word", words1[k].getWord().toString());
                                temp_json.put("PronAccuracy", words1[k].getPronAccuracy().toString());
                                temp_json.put("PronFluency", words1[k].getPronFluency().toString());
                                words.add(temp_json);
                            }
                    }
                }

                result.setWrong_words(words);
                result.setTotal_words_count(words1.length-1);
                result.setWrong_words_count(wrong_words);


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
    public JSONObject getCorpusesByGroupId(String cpsgrpId) {
        List<CpsrcdDO> corpuses = cpsrcdManager.listByCpsgrpId(cpsgrpId);
        CpsgrpDO cpsgrp = cpsgrpMapper.selectById(cpsgrpId);
        JSONObject json = new JSONObject();
        json.put("id",cpsgrp.getId());
        json.put("name",cpsgrp.getName());
        json.put("type",cpsgrp.getType());
        json.put("description",cpsgrp.getDescription());
        json.put("endTime",cpsgrp.getEndTime());
        json.put("creator",cpsgrp.getCreator());
        json.put("gmtCreate",cpsgrp.getGmtCreate());
        json.put("gmtModified",cpsgrp.getGmtModified());
        List<JSONObject> list = new ArrayList<>();
        for(int i=0;i<corpuses.size();i++){
            JSONObject o = new JSONObject();
            //如果list为空则创建list的第一项
            if(corpuses.get(i).getType()==1) {
                //否则遍历list，查看是否已经存在对应type的项
                boolean exist = false;
                //假设所有的corpus_list的项都已经满了
                boolean full =true;
                for(int j=0;j<list.size();j++){
                    int temp_type = (int)list.get(j).get("type");
                    if(temp_type==1)
                        exist = true;
                    List<JSONObject> temp_json= (List<JSONObject>)list.get(j).get("corpus_list");
                    if(temp_json.size()<4&&temp_type==1)
                        full=false;
                }
                //如果list为空,或者list中不存在对应type的项,或者存在但已满则新建json加入corpus_list
                if(exist==false||full==true||list.isEmpty()) {
                    o.put("type",corpuses.get(i).getType());
                    List<JSONObject> corpus_list =new ArrayList<>();
                    JSONObject corpus_json = new JSONObject();
                    corpus_json.put("id",corpuses.get(i).getId());
                    corpus_json.put("refText",corpuses.get(i).getRefText());
                    corpus_json.put("order",corpuses.get(i).getOrder());
                    corpus_json.put("pinyin",corpuses.get(i).getPinyin());
                    corpus_json.put("level",corpuses.get(i).getLevel());
                    corpus_list.add(corpus_json);
                    o.put("corpus_list",corpus_list);
                    list.add(o);
                }else{
                    //找到list中对应的项并插入
                    for(int j=0;j<list.size();j++){
                        List<JSONObject> temp_corpus_list = (List<JSONObject>)list.get(j).get("corpus_list");
                        if((int)list.get(j).get("type")==1&&temp_corpus_list.size()<4){
                            JSONObject temp_json = new JSONObject();
                            temp_json.put("id",corpuses.get(i).getId());
                            temp_json.put("refText",corpuses.get(i).getRefText());
                            temp_json.put("order",corpuses.get(i).getOrder());
                            temp_json.put("pinyin",corpuses.get(i).getPinyin());
                            temp_json.put("level",corpuses.get(i).getLevel());
                            temp_corpus_list.add(temp_json);
                        }
                    }
                }
            }else if(corpuses.get(i).getType()==2){
                boolean exist = false;
                boolean full =true;
                for(int j=0;j<list.size();j++){
                    int temp_type = (int)list.get(j).get("type");
                    if(temp_type==2)
                        exist = true;
                    List<JSONObject> temp_json= (List<JSONObject>)list.get(j).get("corpus_list");
                    if(temp_json.size()<2&&temp_type==2)
                        full=false;
                }
                if(exist==false||full==true||list.isEmpty()) {
                    o.put("type",corpuses.get(i).getType());
                    List<JSONObject> corpus_list =new ArrayList<>();
                    JSONObject corpus_json = new JSONObject();
                    corpus_json.put("id",corpuses.get(i).getId());
                    corpus_json.put("refText",corpuses.get(i).getRefText());
                    corpus_json.put("order",corpuses.get(i).getOrder());
                    corpus_json.put("pinyin",corpuses.get(i).getPinyin());
                    corpus_json.put("level",corpuses.get(i).getLevel());
                    corpus_list.add(corpus_json);
                    o.put("corpus_list",corpus_list);
                    list.add(o);
                }else{
                    for(int j=0;j<list.size();j++){
                        List<JSONObject> temp_corpus_list = (List<JSONObject>)list.get(j).get("corpus_list");
                        if((int)list.get(j).get("type")==2&&temp_corpus_list.size()<2){
                            JSONObject temp_json = new JSONObject();
                            temp_json.put("id",corpuses.get(i).getId());
                            temp_json.put("refText",corpuses.get(i).getRefText());
                            temp_json.put("order",corpuses.get(i).getOrder());
                            temp_json.put("pinyin",corpuses.get(i).getPinyin());
                            temp_json.put("level",corpuses.get(i).getLevel());
                            temp_corpus_list.add(temp_json);
                        }
                    }
                }
            } else if (corpuses.get(i).getType()==3) {
                o.put("type",corpuses.get(i).getType());
                List<JSONObject> corpus_list =new ArrayList<>();
                JSONObject corpus_json = new JSONObject();
                corpus_json.put("id",corpuses.get(i).getId());
                corpus_json.put("refText",corpuses.get(i).getRefText());
                corpus_json.put("order",corpuses.get(i).getOrder());
                corpus_json.put("pinyin",corpuses.get(i).getPinyin());
                corpus_json.put("level",corpuses.get(i).getLevel());
                corpus_list.add(corpus_json);
                o.put("corpus_list",corpus_list);
                list.add(o);
            } else if (corpuses.get(i).getType()==5) {
                o.put("type",corpuses.get(i).getType());
                List<JSONObject> corpus_list =new ArrayList<>();
                JSONObject corpus_json = new JSONObject();
                corpus_json.put("id",corpuses.get(i).getId());
                corpus_json.put("refText",corpuses.get(i).getRefText());
                corpus_json.put("order",corpuses.get(i).getOrder());
                corpus_json.put("pinyin",corpuses.get(i).getPinyin());
                corpus_json.put("level",corpuses.get(i).getLevel());
                corpus_list.add(corpus_json);
                o.put("corpus_list",corpus_list);
                list.add(o);
            }else {
                o.put("type",corpuses.get(i).getType());
                List<JSONObject> corpus_list =new ArrayList<>();
                JSONObject corpus_json = new JSONObject();
                corpus_json.put("id",corpuses.get(i).getId());
                corpus_json.put("refText",corpuses.get(i).getRefText());
                corpus_json.put("order",corpuses.get(i).getOrder());
                corpus_json.put("pinyin",corpuses.get(i).getPinyin());
                corpus_json.put("level",corpuses.get(i).getLevel());
                corpus_list.add(corpus_json);
                o.put("corpus_list",corpus_list);
                list.add(o);
            }
        }
        json.put("cpsrcdList",list);
        return json;
    }
}
