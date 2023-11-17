package net.ecnu.feign;

import net.ecnu.util.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Joshua
 * @description: 自研测评接口调用Feign类
 * @date 2023/11/11 14:06
 */
@FeignClient(name = "corpus-service",url = "http://59.78.194.202:8081/corpus-server")
public interface EvaluateFeignService {
    /**
     * jieba分词接口
     * @param refText 待处理文本
     * **/
    @PostMapping("/api/evaluate/v1/jieba")
    JsonData dealTextDataByJieBa(@RequestParam("refText") String refText);

    /**
     * 自研语音评测接口
     * @param audio 音频文件（.wav格式）
     * @param refText 音频文本（jieba分词处理后的文本数据）
     * **/
    @PostMapping(value = "/api/evaluate/v1/eval_test"
            ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    JsonData evalByZY(@RequestPart("audio") MultipartFile audio, @RequestPart("refText") String refText);


}
