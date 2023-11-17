package net.ecnu.controller;

import net.ecnu.enums.EvaluateTypeEnum;
import net.ecnu.service.EvaluateService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@RestController
@RequestMapping("/api/evaluate/v1")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;

    /**
     * 语音评测（讯飞版）——异步返回结果
     */
    @Deprecated
    @PostMapping("eval_xf2")
    public JsonData eval_xf2(@RequestParam(value = "audio", required = true) MultipartFile audio,
                             @RequestParam(value = "refText", required = true) String refText,
                             @RequestParam(value = "category", required = true) String category,
                             @RequestParam(value = "pinyin", required = false) String pinyin) {
        long startTime = System.currentTimeMillis();
        File convertAudio = evaluateService.convert_lyw(audio);
        System.out.println("语音格式转换耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        Object data = evaluateService.evaluateByXF2(convertAudio, refText, pinyin, category);
        return JsonData.buildSuccess(data);
    }

    /**
     * 语音评测（讯飞版）——同步返回结果
     */
    @PostMapping("eval_xf")
    public JsonData eval_xf(@RequestParam(value = "audio", required = true) MultipartFile audio,
                            @RequestParam(value = "refText", required = true) String refText,
                            @RequestParam(value = "category", required = true) String category,
                            @RequestParam(value = "pinyin", required = false) String pinyin,
                            @RequestParam(value = "cpsrcdId",required = false) String cpsrcdId,
                            @RequestParam(value = "cpsgrpId",required = false) String cpsgrpId,
                            @RequestParam(value = "evaluateType",required = false) Integer evaluateType
                            ) {
        /**向老版本兼容，评测方式若不传，则默认为科大讯飞评测**/
        evaluateType = Objects.isNull(evaluateType) ? EvaluateTypeEnum.XF_EVALUATE.getCode() : evaluateType;
        Object data = evaluateService.evaluateByXF(audio, refText, pinyin, category,cpsrcdId,cpsgrpId,evaluateType);
        return JsonData.buildSuccess(data);
    }

    /**
     * 讯飞 评测数据 提供接口
     */
    @PostMapping("eval_test")
    public JsonData eval_test(@RequestParam(value = "audio", required = true) MultipartFile audio,
                              @RequestParam(value = "refText", required = true) String refText,
                              @RequestParam(value = "category", required = true) String category,
                              @RequestParam(value = "appId", required = true) String appId,
                              @RequestParam(value = "apiSecret", required = true) String apiSecret,
                              @RequestParam(value = "apiKey", required = true) String apiKey) {
        long startTime = System.currentTimeMillis();
        File convertAudio = evaluateService.convert_lyw(audio);
        System.out.println("语音格式转换耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        Object data = evaluateService.evaluateByXFWithSecretAndKey(convertAudio, refText, "", category, appId, apiSecret, apiKey);
        return JsonData.buildSuccess(data);
    }

    /**
     * 语音评测（腾讯版）
     */
    @Deprecated
    @PostMapping("eval")
    public JsonData evaluate(@RequestParam(value = "audio", required = true) MultipartFile audio,
                             @RequestParam(value = "refText", required = true) String refText,
                             @RequestParam(value = "pinyin", required = false) String pinyin,
                             @RequestParam(value = "evalMode", required = true) long evalMode) {
        long startTime = System.currentTimeMillis();
        File convert = evaluateService.convert_lyw(audio);
        System.out.println("语音格式转换耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        Object data = evaluateService.evaluate(convert, refText, pinyin, evalMode);
        return JsonData.buildSuccess(data);
    }

    /**
     * 音频格式转换
     */
    @PostMapping(value = "convert", produces = "audio/wav")
    public ResponseEntity<Resource> convert(@RequestParam(value = "audio", required = true) MultipartFile audio) {
        String path = evaluateService.convert_lyw(audio).getPath();
        String contentDisposition = ContentDisposition
                .builder("attachment")
                .filename(path)
                .build().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(path));
    }


//    @Deprecated
//    @GetMapping("details")
//    public JsonData details(@RequestParam String cpsgrpId) {
//        Object data = evaluateService.getCorpusesByGroupId(cpsgrpId);
//        return JsonData.buildSuccess(data);
//    }
}
