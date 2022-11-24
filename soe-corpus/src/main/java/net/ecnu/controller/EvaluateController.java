package net.ecnu.controller;

import com.alibaba.fastjson.JSONObject;
import net.ecnu.service.EvaluateService;
import net.ecnu.service.FileService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/evaluate/v1")
public class EvaluateController {

    @Autowired
    FileService fileService;

    @Autowired
    private EvaluateService evaluateService;

    @PostMapping("upload")
    @ResponseBody
    public Result sentenceEvaluate(@RequestPart("audio") MultipartFile audio, @RequestParam("text") String text,
                                   @RequestParam("pinyin") String pinyin, @RequestParam("mode") String mode) {
        Result result = fileService.evaluate(audio, text, pinyin, mode);
        return result;
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

    @GetMapping("details")
    public JSONObject details(@RequestParam String cpsgrpId) {
        JSONObject result = fileService.getCorpusesByGroupId(cpsgrpId);
        return result;
    }

}
