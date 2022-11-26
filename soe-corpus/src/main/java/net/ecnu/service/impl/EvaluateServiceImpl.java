package net.ecnu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import com.tencentcloudapi.soe.v20180724.models.WordRsp;
import net.ecnu.constant.SOEConst;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.vo.EvalResultVO;
import net.ecnu.service.EvaluateService;
import net.ecnu.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * <p>
 * 语料原型 服务实现类
 * </p>
 *
 * @author LYW
 * @since 2022-10-20
 */
@Service
public class EvaluateServiceImpl implements EvaluateService {

    @Value("${tencent.soe.secret-id}")
    private String secretId;

    @Value("${tencent.soe.secret-key}")
    private String secretKey;

    @Autowired
    private CpsrcdManager cpsrcdManager;

    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Override
    public Object evaluate(MultipartFile audio, String refText, String pinyin, long evalMode) {
        File file = FileUtil.transferToFile(audio);
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bytes == null) return null;
        // 实例化腾讯SOE服务的Credential认证对象
        Credential cred = new Credential(secretId, secretKey);
        // 初始化评测请求
        TransmitOralProcessWithInitRequest req = initSOERequest(refText, evalMode);
        // 发送评测请求并接收结果
        TransmitOralProcessWithInitResponse resp = sendEvalReqAndGetResp(req, cred, bytes);
        if (resp == null) return null;
        System.out.println(TransmitOralProcessWithInitResponse.toJsonString(resp));
        // 聚合生成evalResultVO对象
        EvalResultVO evalResultVO = new EvalResultVO();
        evalResultVO.setSuggestedScore(Double.valueOf(resp.getSuggestedScore()));
        evalResultVO.setPronFluency(Double.valueOf(resp.getPronFluency()));
        evalResultVO.setPronAccuracy(Double.valueOf(resp.getPronAccuracy()));
        evalResultVO.setPronCompletion(Double.valueOf(resp.getPronCompletion()));
        //统计总字数与错字数
        int wrongWordCount = 0;
        for (WordRsp word : resp.getWords()) {
            if (word.getPronAccuracy() < SOEConst.ERR_SCORE_LINE) wrongWordCount++;
        }
        evalResultVO.setTotalWordCount(refText.length());
        evalResultVO.setWrongWordCount(wrongWordCount);
        //返回结果
        return evalResultVO;
    }


    @Override
    public File convert(MultipartFile file) {
        File source = FileUtil.transferToFile(file);
        File target = new File("/Users/lyw/Desktop/tmp.wav");
        target.deleteOnExit();// 在虚拟机终止时，请求删除此抽象路径名表示的文件或目录。
        // 创建音频属性实例
        AudioAttributes audio = new AudioAttributes();
        // 设置编码 libmp3lame pcm_s16le
        audio.setCodec("pcm_s16le");
        // 音频比特率
        audio.setBitRate(16000);
        // 声道 1 =单声道，2 =立体声
        audio.setChannels(1);
        // 采样率
        audio.setSamplingRate(16000);
        // 转码属性实例
        EncodingAttributes attrs = new EncodingAttributes();
        // 转码格式
        attrs.setOutputFormat("wav");
        attrs.setAudioAttributes(audio);

        MultimediaObject sourceObj = new MultimediaObject(source);
        try {
            Encoder encoder = new Encoder();
            encoder.encode(sourceObj, target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 发送评测请求并获取结果
     */
    private TransmitOralProcessWithInitResponse sendEvalReqAndGetResp(
            TransmitOralProcessWithInitRequest req, Credential cred, byte[] data) {
        try {
            int pkgNum = (int) Math.ceil((double) data.length / SOEConst.PKG_SIZE);
            TransmitOralProcessWithInitResponse resp = null;
            SoeClient client = new SoeClient(cred, "");
            for (int i = 1; i <= pkgNum; i++) {
                // 获取lastIndex
                int lastIndex = (i == pkgNum ? data.length : i * SOEConst.PKG_SIZE);
                // 截取对应编码后的文件
                byte[] buf = Arrays.copyOfRange(data, (i - 1) * SOEConst.PKG_SIZE, lastIndex);
                String base64Str = Base64.getEncoder().encodeToString(buf);
                req.setUserVoiceData(base64Str);
                req.setSeqId((long) i);
                req.setIsEnd(i == pkgNum ? 1L : 0L);
                long l = System.currentTimeMillis();
                TransmitOralProcessWithInitResponse respTmp = client.TransmitOralProcessWithInit(req);
                System.out.println("第" + i + "次分片传输耗时：" + (System.currentTimeMillis() - l) + "ms");
                if (Objects.equals(respTmp.getStatus(), "Finished")) {
                    System.out.println("评测结束");
                    resp = respTmp;
                }
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化评测请求
     */
    private TransmitOralProcessWithInitRequest initSOERequest(String refText, long evalMode) {
        TransmitOralProcessWithInitRequest req = new TransmitOralProcessWithInitRequest();
        req.setVoiceEncodeType(1L);  //语音数据类型1:pcm
        req.setVoiceFileType(2L); //语音文件类型1: raw，2: wav，3: mp3，4: speex
        req.setSessionId(UUID.randomUUID().toString()); //唯一标识
        req.setRefText(refText);//设置评测参考文本
        req.setTextMode(0L); //文本格式.0普通文本 1,音素结构
        req.setWorkMode(0L); //0,流式分片,1一次性评测
        req.setEvalMode(evalMode);//评测模式
        req.setScoreCoeff(1.0f); //评估难度（1～4）
        req.setServerType(1L); //服务类型.0英文,1中文
        req.setIsAsync(0L); //异步
        req.setIsQuery(0L); //轮询
        return req;
    }


//    @Override
//    public Result evaluate(MultipartFile audio, String text, String pinyin, String mode) {
//        Result result = new Result();
//        try {
//            File wav_file = FileUtil.MultipartFile2File(audio);
//            File file =new File("output.mp3");
//            file = SOEFileUtil.Wav2mp3(wav_file,file);
//            int PKG_SIZE = 2 * 1024; //分片大小
//            String secretId = "AKIDZNBuVGvNrnbJoBYv9gr3EQWUJ8w1DPWS";
//            String secretKey = "YxBE6oIZgh7OZYM2Zv7SghigV96g6LNw";
//            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
//            Credential cred = new Credential(secretId, secretKey);
//            // 实例化要请求产品的client对象,clientProfile是可选的
//            SoeClient client = new SoeClient(cred, "");
//            String sessionId = UUID.randomUUID().toString();
//            TransmitOralProcessWithInitRequest req = new TransmitOralProcessWithInitRequest();
//            req.setVoiceEncodeType(1L);  //语音数据类型1:pcm
//            req.setVoiceFileType(3L); //语音文件类型1: raw，2: wav，3: mp3，4: speex
//            req.setSessionId(sessionId); //唯一标识
//            if(pinyin.isEmpty()){//普通评测模式
//                req.setRefText(text.trim());
//                req.setTextMode(0L); //文本格式.0普通文本 1,音素结构
//            }else{//指定拼音评测模式
//                String s = SOEWordUtil.formatText(text,pinyin);
//                req.setRefText(s); //文本
//                req.setTextMode(1L); //文本格式.0普通文本 1,音素结构
//            }
//            req.setWorkMode(0L); //0,流式分片,1一次性评测
//            if("0".equals(mode)){
//                req.setEvalMode(0L); //评估模式,0,单词.1,句子,2,段落,3自由说,4单词纠错
//            }else if("1".equals(mode)||"2".equals(mode)){
//                req.setEvalMode(1L);
//            }else if("3".equals(mode)||"5".equals(mode)){
//                req.setEvalMode(2L);
//            }else{
//                return null;
//            }
//            req.setScoreCoeff(1.0f); //评估难度
//            req.setServerType(1L); //服务类型.0英文,1中文
//            req.setIsAsync(0L); //异步
//            req.setIsQuery(0L); //轮询
//            TransmitOralProcessWithInitResponse resp = null;
//            //将文件装换成base64
//            //byte[] data = Files.refadAllBytes(Paths.get(file));
//            byte[] data = SOEFileUtil.File2byte(file);
//            int pkgNum = (int) Math.ceil((double) data.length / PKG_SIZE);
//            for (int i = 1; i <= pkgNum; i++) {
//                int lastIndex = i * PKG_SIZE;
//                if (i == pkgNum) {
//                    lastIndex = data.length;
//                }
//                byte[] buf = Arrays.copyOfRange(data, (i - 1) * PKG_SIZE, lastIndex);
//                //String base64Str = new sun.misc.BASE64Encoder().encode(buf);
//                String base64Str = Base64.getEncoder().encodeToString(buf);
//                req.setUserVoiceData(base64Str);
//                req.setSeqId((long) i);
//                if (i == pkgNum) {
//                    req.setIsEnd(1L);
//                } else {
//                    req.setIsEnd(0L);
//                }
//                resp = client.TransmitOralProcessWithInit(req);
//                // 输出json格式的字符串回包
//                //System.out.println(TransmitOralProcessWithInitResponse.toJsonString(resp));
//            }
//            if (resp==null){
//                result.setWrongWordsCount(0);
//                List<JSONObject> list = new ArrayList<>();
//                JSONObject object = new JSONObject();
//                object.put("words",0);
//                list.add(object);
//                if (list == null)
//                    System.out.println("这里为null");
//                result.setWrongwWords(list);
//                result.setPronAccuracy(0);
//                result.setPronFluency(0);
//                result.setPronCompletion(0);
//                result.setTotalWordsCount(0);
//                result.setSuggestedScore(0);
//            }
//            result.setSuggestedScore(Float.valueOf(resp.getSuggestedScore().toString()));
//            result.setPronAccuracy(Float.valueOf(resp.getPronAccuracy().toString()));
//            result.setPronFluency(Float.valueOf(resp.getPronFluency().toString()));
//            result.setPronCompletion(Float.valueOf(resp.getPronCompletion().toString()));
//            WordRsp[] resp_words = resp.getWords();
//            int wrong_words =0,total_words=0;
//            //将所有得分不超过90分的汉字加入返回集合
//            List<JSONObject> words = new ArrayList<>();
//            if ("Finished".equals(resp.getStatus())){
//                for(int k = 0; k< resp_words.length; k++){
//                    if(!"*".equals(resp_words[k].getWord())){
//                        total_words++;
//                        if(Float.valueOf(resp_words[k].getPronAccuracy())<90|| Float.valueOf(resp_words[k].getPronFluency())<0.90){
//                            wrong_words++;//统计错字字数
//                            JSONObject temp_json = new JSONObject();
//                            temp_json.put("word", resp_words[k].getWord());
//                            temp_json.put("PronAccuracy", Float.valueOf(resp_words[k].getPronAccuracy().toString()));
//                            temp_json.put("PronFluency", Float.valueOf(resp_words[k].getPronFluency().toString()));
//                            words.add(temp_json);
//                        }
//                    }
//                }
//            }
//            result.setWrongwWords(words);
//            result.setTotalWordsCount(total_words);
//            result.setWrongWordsCount(wrong_words);
//        } catch (TencentCloudSDKException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        } catch (InputFormatException e) {
//            throw new RuntimeException(e);
//        } catch (EncoderException e) {
//            throw new RuntimeException(e);
//        }
//        File outputfile = new File("output.mp3");
//        if (outputfile.exists())
//            outputfile.delete();
//        return result;
//    }

    @Override
    public JSONObject getCorpusesByGroupId(String cpsgrpId) {
        List<CpsrcdDO> corpuses = cpsrcdManager.listByCpsgrpId(cpsgrpId);
        CpsgrpDO cpsgrp = cpsgrpMapper.selectById(cpsgrpId);
        JSONObject json = new JSONObject();
        json.put("id", cpsgrp.getId());
        json.put("name", cpsgrp.getName());
        json.put("type", cpsgrp.getType());
        json.put("description", cpsgrp.getDescription());
        json.put("endTime", cpsgrp.getEndTime());
        json.put("creator", cpsgrp.getCreator());
        json.put("gmtCreate", cpsgrp.getGmtCreate());
        json.put("gmtModified", cpsgrp.getGmtModified());
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < corpuses.size(); i++) {
            JSONObject o = new JSONObject();
            //如果list为空则创建list的第一项
            if (corpuses.get(i).getType() == 1) {
                boolean exist = false;
                //否则遍历list，查看是否已经存在对应type的项
                for (int j = 0; j < list.size(); j++) {
                    if ((int) list.get(j).get("type") == 1)
                        exist = true;
                }
                //如果list为空,或者list中不存在对应type的项则新建json加入corpus_list
                if (exist == false || list.isEmpty()) {
                    o.put("type", corpuses.get(i).getType());
                    List<JSONObject> corpus_list = new ArrayList<>();
                    JSONObject corpus_json = new JSONObject();
                    corpus_json.put("id", corpuses.get(i).getId());
                    corpus_json.put("refText", corpuses.get(i).getRefText());
                    corpus_json.put("order", corpuses.get(i).getOrder());
                    corpus_json.put("pinyin", corpuses.get(i).getPinyin());
                    corpus_json.put("level", corpuses.get(i).getLevel());
                    corpus_list.add(corpus_json);
                    o.put("corpus_list", corpus_list);
                    list.add(o);
                } else {
                    //找到list中对应的项并插入
                    for (int j = 0; j < list.size(); j++) {
                        List<JSONObject> temp_corpus_list = (List<JSONObject>) list.get(j).get("corpus_list");
                        if ((int) list.get(j).get("type") == 1) {
                            JSONObject temp_json = new JSONObject();
                            temp_json.put("id", corpuses.get(i).getId());
                            temp_json.put("refText", corpuses.get(i).getRefText());
                            temp_json.put("order", corpuses.get(i).getOrder());
                            temp_json.put("pinyin", corpuses.get(i).getPinyin());
                            temp_json.put("level", corpuses.get(i).getLevel());
                            temp_corpus_list.add(temp_json);
                        }
                    }
                }
            } else if (corpuses.get(i).getType() == 2) {
                boolean exist = false;
                for (int j = 0; j < list.size(); j++) {
                    if ((int) list.get(j).get("type") == 2)
                        exist = true;
                }
                if (exist == false || list.isEmpty()) {
                    o.put("type", corpuses.get(i).getType());
                    List<JSONObject> corpus_list = new ArrayList<>();
                    JSONObject corpus_json = new JSONObject();
                    corpus_json.put("id", corpuses.get(i).getId());
                    corpus_json.put("refText", corpuses.get(i).getRefText());
                    corpus_json.put("order", corpuses.get(i).getOrder());
                    corpus_json.put("pinyin", corpuses.get(i).getPinyin());
                    corpus_json.put("level", corpuses.get(i).getLevel());
                    corpus_list.add(corpus_json);
                    o.put("corpus_list", corpus_list);
                    list.add(o);
                } else {
                    for (int j = 0; j < list.size(); j++) {
                        List<JSONObject> temp_corpus_list = (List<JSONObject>) list.get(j).get("corpus_list");
                        if ((int) list.get(j).get("type") == 2) {
                            JSONObject temp_json = new JSONObject();
                            temp_json.put("id", corpuses.get(i).getId());
                            temp_json.put("refText", corpuses.get(i).getRefText());
                            temp_json.put("order", corpuses.get(i).getOrder());
                            temp_json.put("pinyin", corpuses.get(i).getPinyin());
                            temp_json.put("level", corpuses.get(i).getLevel());
                            temp_corpus_list.add(temp_json);
                        }
                    }
                }
            } else {
                o.put("type", corpuses.get(i).getType());
                List<JSONObject> corpus_list = new ArrayList<>();
                JSONObject corpus_json = new JSONObject();
                corpus_json.put("id", corpuses.get(i).getId());
                corpus_json.put("refText", corpuses.get(i).getRefText());
                corpus_json.put("order", corpuses.get(i).getOrder());
                corpus_json.put("pinyin", corpuses.get(i).getPinyin());
                corpus_json.put("level", corpuses.get(i).getLevel());
                corpus_list.add(corpus_json);
                o.put("corpus_list", corpus_list);
                list.add(o);
            }
        }
        json.put("cpsrcdList", list);
        return json;
    }

}
