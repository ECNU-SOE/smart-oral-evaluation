package net.ecnu.controller;

import com.alibaba.fastjson.JSONObject;
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

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/evaluate/v1")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;


    @PostMapping("upload")
    @Deprecated
    public Result sentenceEvaluate(@RequestPart("audio") MultipartFile audio, @RequestParam("text") String text,
                                   @RequestParam("pinyin") String pinyin, @RequestParam("mode") String mode) {
//        Result result = evaluateService.evaluate(audio, text, pinyin, mode);
        return null;
    }

    /**
     * 语音评测
     */
    @PostMapping("eval")
    public JsonData evaluate(@RequestParam(value = "audio", required = true) MultipartFile audio,
                             @RequestParam(value = "refText", required = true) String refText,
                             @RequestParam(value = "pinyin", required = false) String pinyin,
                             @RequestParam(value = "evalMode", required = true) long evalMode) {
        Object data = evaluateService.evaluate(audio, refText, pinyin, evalMode);
        return JsonData.buildSuccess(data);
    }

    /**
     * 格式转换
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
    public JSONObject details(@RequestParam String cpsgrpId) {
        JSONObject result = evaluateService.getCorpusesByGroupId(cpsgrpId);
        return result;
    }

}
