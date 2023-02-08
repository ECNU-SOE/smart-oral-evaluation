package net.ecnu.controller;

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

@RestController
@RequestMapping("/api/evaluate/v1")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;

    /**
     * 讯飞——语音评测
     */
    @PostMapping("eval_xf")
    public JsonData eval_xf(@RequestParam(value = "audio", required = true) MultipartFile audio,
                            @RequestParam(value = "refText", required = true) String refText,
                            @RequestParam(value = "pinyin", required = false) String pinyin,
                            @RequestParam(value = "evalMode", required = true) long evalMode) {
        long startTime = System.currentTimeMillis();
        File convert = evaluateService.convert(audio);
        System.out.println("语音格式转换耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        Object data = evaluateService.evaluateByXF(convert, refText, pinyin, evalMode);
        return JsonData.buildSuccess(data);
    }

    /**
     * 腾讯云——语音评测
     */
    @PostMapping("eval")
    public JsonData evaluate(@RequestParam(value = "audio", required = true) MultipartFile audio,
                             @RequestParam(value = "refText", required = true) String refText,
                             @RequestParam(value = "pinyin", required = false) String pinyin,
                             @RequestParam(value = "evalMode", required = true) long evalMode) {
        long startTime = System.currentTimeMillis();
        File convert = evaluateService.convert(audio);
        System.out.println("语音格式转换耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        Object data = evaluateService.evaluate(convert, refText, pinyin, evalMode);
        return JsonData.buildSuccess(data);
    }

    /**
     * 音频格式转换
     */
    @PostMapping(value = "convert", produces = "audio/wav")
    public ResponseEntity<Resource> convert(@RequestParam(value = "audio", required = true) MultipartFile audio) {
        String path = evaluateService.convert(audio).getPath();
        String contentDisposition = ContentDisposition
                .builder("attachment")
                .filename(path)
                .build().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(path));
    }


    @GetMapping("details")
    public JsonData details(@RequestParam String cpsgrpId) {
        Object data = evaluateService.getCorpusesByGroupId(cpsgrpId);
        return JsonData.buildSuccess(data);
    }
}
