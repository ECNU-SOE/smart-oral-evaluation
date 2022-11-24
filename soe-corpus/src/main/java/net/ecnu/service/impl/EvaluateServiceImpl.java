package net.ecnu.service.impl;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import com.tencentcloudapi.soe.v20180724.models.WordRsp;
import net.ecnu.constant.SOEConst;
import net.ecnu.model.vo.EvalResultVO;
import net.ecnu.service.EvaluateService;
import net.ecnu.util.FileUtil;
import net.ecnu.util.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
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
}
