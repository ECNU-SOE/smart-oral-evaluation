package net.ecnu.controller;

import net.ecnu.model.result.Result;
import net.ecnu.service.EvaluateService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/evaluate/v1")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;


    @PostMapping("upload")
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

    @GetMapping("details")
    public JsonData details(@RequestParam String cpsgrpId) {
        Object data = evaluateService.getCorpusesByGroupId(cpsgrpId);
        return JsonData.buildSuccess(data);
    }
}
