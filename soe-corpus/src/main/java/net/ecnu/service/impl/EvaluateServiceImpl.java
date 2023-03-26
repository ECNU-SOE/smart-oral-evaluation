package net.ecnu.service.impl;

import cn.hutool.json.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import com.tencentcloudapi.soe.v20180724.models.WordRsp;
import net.ecnu.constant.SOEConst;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.mapper.EvalRecordMapper;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.EvalListener;
import net.ecnu.model.EvalRecordDO;
import net.ecnu.model.vo.EvalResultVO;
import net.ecnu.service.EvaluateService;
import net.ecnu.util.CommonUtil;
import net.ecnu.util.FileUtil;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.*;
//import ws.schild.jave.encode.AudioAttributes;
//import ws.schild.jave.encode.EncodingAttributes;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;


import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;

import java.io.File;


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

    @Value("${xunfei.ve.host-url}")
    private String hostUrl;

    @Value("${xunfei.ve.appid}")
    private String appid;

    @Value("${xunfei.ve.api-secret}")
    private String apiSecret;

    @Value("${xunfei.ve.api-key}")
    private String apiKey;


    @Autowired
    private CpsrcdManager cpsrcdManager;

    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Autowired
    private EvalRecordMapper evalRecordMapper;

    @Override
    public Object evaluate(File file, String refText, String pinyin, long evalMode) {
//        File file = FileUtil.transferToFile(audio);
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
        // 统计总字数与错字数
        int wrongWordCount = 0;
        for (WordRsp word : resp.getWords()) {
            if (word.getPronAccuracy() < SOEConst.ERR_SCORE_LINE) wrongWordCount++;
        }
        // 错字包括未读字
        int totalWordCount = CommonUtil.countWord(refText);
        wrongWordCount += totalWordCount - resp.getWords().length;
        evalResultVO.setTotalWordCount(totalWordCount);
        evalResultVO.setWrongWordCount(wrongWordCount);
        //返回结果
        return evalResultVO;
    }


    @Override
    public File convert_lyw(MultipartFile file) {
        File source = FileUtil.transferToFile(file);
        File target = new File("eval_audio.mp3");
        target.deleteOnExit();// 在虚拟机终止时，请求删除此抽象路径名表示的文件或目录。
        // 创建音频属性实例
        AudioAttributes audio = new AudioAttributes();
        // 设置编码 libmp3lame pcm_s16le
        audio.setCodec("libmp3lame");
        // 音频比特率
        audio.setBitRate(16000);
        // 声道 1 =单声道，2 =立体声
        audio.setChannels(1);
        // 采样率
        audio.setSamplingRate(16000);
        // 转码属性实例
        EncodingAttributes attrs = new EncodingAttributes();
        // 转码格式
//        attrs.setOutputFormat("wav");
        attrs.setFormat("mp3");
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

//    @Override
//    public File convert_tgx(MultipartFile file) {
//        File source = FileUtil.transferToFile(file);
//        File target = new File("C:\\Users\\tgx\\Desktop\\tmp.mp3");
//
//        try {
//            AudioAttributes audio = new AudioAttributes();
//            audio.setCodec("libmp3lame");
//            audio.setBitRate(128000);
//            audio.setChannels(2);
//            audio.setSamplingRate(44100);
//            audio.setVolume(256);
//
//            EncodingAttributes attrs = new EncodingAttributes();
//            attrs.setFormat("mp3");
//            attrs.setAudioAttributes(audio);
//            // attrs.setOffset(5F);
//            // attrs.setDuration(30F);
//            Encoder encoder = new Encoder();
//            encoder.encode(new MultimediaObject(source), target, attrs);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return target;
//    }


    /**
     * 语音评测（讯飞版）——异步
     */
    @Override
    public Object evaluateByXF2(File audio, String refText, String pinyin, String category) {

        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);// 构建鉴权url
        //将url中的 schema http://和https://分别替换为ws:// 和 wss://
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();
        EvalListener evalListener = new EvalListener();
        evalListener.setFile(audio);
        evalListener.setText(refText);
        evalListener.setCategory(category);//category校验
        WebSocket webSocket = client.newWebSocket(request, evalListener);
//        long beginTime = (new Date()).getTime();
//
//        while (evalListener.getEvalRes() == null) {
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        long endTime = (new Date()).getTime();
//        System.out.println("等待评测结果耗时：" + (endTime - beginTime) + "ms");
//        return ((cn.hutool.json.JSONObject) evalListener.getEvalRes().get("xml_result")).get(category);
        System.out.println("return message");
        return "message";
    }

    /**
     * 语音评测（讯飞版）
     */
    @Override
    public Object evaluateByXF(File audio, String refText, String pinyin, String category) {
        System.out.println("text:" + refText);

        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);// 构建鉴权url
        //将url中的 schema http://和https://分别替换为ws:// 和 wss://
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();
        EvalListener evalListener = new EvalListener();
        evalListener.setFile(audio);
        evalListener.setText(refText);
        evalListener.setCategory(category);//category校验
        WebSocket webSocket = client.newWebSocket(request, evalListener);
        //循环等待结果
        while (evalListener.getEvalRes() == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //新增评测记录evalRecord
        Object evalJsonRes = ((JSONObject) evalListener.getEvalRes().get("xml_result")).get(category);
        EvalRecordDO evalRecordDO = new EvalRecordDO();
        evalRecordDO.setAlgRes(evalJsonRes.toString());
        evalRecordMapper.insert(evalRecordDO);
        //返回结果
        return evalJsonRes;

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
//    public Object getCorpusesByGroupId(String cpsgrpId) {
//        List<CpsrcdDO> cpsrcdDOS = cpsrcdManager.listByCpsgrpId(cpsgrpId);
//        CpsgrpDO cpsgrp = cpsgrpMapper.selectById(cpsgrpId);
//        CpsgrpVO2 cpsgrpVO = new CpsgrpVO2();
//        BeanUtils.copyProperties(cpsgrp, cpsgrpVO);
//        List<JSONObject> list = new ArrayList<>();
//        for (CpsrcdDO cpsrcd : cpsrcdDOS) {
//            JSONObject o = new JSONObject();
//            //如果list为空则创建list的第一项
//            if (cpsrcd.getType() == 1) {
//                boolean exist = false;
//                //否则遍历list，查看是否已经存在对应type的项
//                for (JSONObject jsonObject : list) {
//                    if ((int) jsonObject.get("type") == 1)
//                        exist = true;
//                }
//                //如果list为空,或者list中不存在对应type的项则新建cpsrcdVO加入corpus_list
//                if (!exist || list.isEmpty()) {
//                    o.put("type", cpsrcd.getType());
//                    List<CpsrcdVO2> cpsrcdVO2s = new ArrayList<>();
//                    CpsrcdVO2 cpsrcdVO2 = new CpsrcdVO2();
//                    BeanUtils.copyProperties(cpsrcd, cpsrcdVO2);
//                    cpsrcdVO2s.add(cpsrcdVO2);
//                    o.put("corpus_list", cpsrcdVO2s);
//                    list.add(o);
//                } else {
//                    //找到list中对应的项并插入
//                    for (JSONObject jsonObject : list) {
//                        List<CpsrcdVO2> temp_list = (List<CpsrcdVO2>) jsonObject.get("corpus_list");
//                        if ((int) jsonObject.get("type") == 1) {
//                            CpsrcdVO2 cpsrcdVO2 = new CpsrcdVO2();
//                            BeanUtils.copyProperties(cpsrcd, cpsrcdVO2);
//                            temp_list.add(cpsrcdVO2);
//                        }
//                    }
//                }
//            } else if (cpsrcd.getType() == 2) {
//                boolean exist = false;
//                for (JSONObject jsonObject : list) {
//                    if ((int) jsonObject.get("type") == 2)
//                        exist = true;
//                }
//                if (!exist || list.isEmpty()) {
//                    o.put("type", cpsrcd.getType());
//                    List<CpsrcdVO2> cpsrcdVO2s = new ArrayList<>();
//                    CpsrcdVO2 cpsrcdVO2 = new CpsrcdVO2();
//                    BeanUtils.copyProperties(cpsrcd, cpsrcdVO2);
//                    cpsrcdVO2s.add(cpsrcdVO2);
//                    o.put("corpus_list", cpsrcdVO2s);
//                    list.add(o);
//                } else {
//                    //找到list中对应的项并插入
//                    for (JSONObject jsonObject : list) {
//                        List<CpsrcdVO2> temp_list = (List<CpsrcdVO2>) jsonObject.get("corpus_list");
//                        if ((int) jsonObject.get("type") == 2) {
//                            CpsrcdVO2 cpsrcdVO2 = new CpsrcdVO2();
//                            BeanUtils.copyProperties(cpsrcd, cpsrcdVO2);
//                            temp_list.add(cpsrcdVO2);
//                        }
//                    }
//                }
//            } else {
//                o.put("type", cpsrcd.getType());
//                List<CpsrcdVO2> cpsrcdVO2s = new ArrayList<>();
//                CpsrcdVO2 cpsrcdVO2 = new CpsrcdVO2();
//                BeanUtils.copyProperties(cpsrcd, cpsrcdVO2);
//                cpsrcdVO2s.add(cpsrcdVO2);
//                o.put("corpus_list", cpsrcdVO2s);
//                list.add(o);
//            }
//
//        }
//        cpsgrpVO.setCpsrcdList(list);
//        return cpsgrpVO;
//    }

    /**
     * 讯飞鉴权
     */
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) {
        try {
            URL url = new URL(hostUrl);
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());
            String builder = "host: " + url.getHost() + "\n" +
                    "date: " + date + "\n" +
                    "GET " + url.getPath() + " HTTP/1.1";
            Charset charset = StandardCharsets.UTF_8;
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);
            //System.err.println(sha);
            String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                    apiKey, "hmac-sha256", "host date request-line", sha);
            //System.err.println(authorization);
            HttpUrl httpUrl = HttpUrl.parse("https://" + url.getHost() + url.getPath()).newBuilder().
                    addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(charset))).
                    addQueryParameter("date", date).//
                            addQueryParameter("host", url.getHost()).//
                            build();
            return httpUrl.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
