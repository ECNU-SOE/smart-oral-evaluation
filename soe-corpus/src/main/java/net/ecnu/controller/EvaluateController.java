package net.ecnu.controller;

import com.alibaba.fastjson.JSONObject;
import net.ecnu.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/evaluate/v1")
public class EvaluateController {

    @Autowired
    EvaluateService fileService;

    @PostMapping("upload")
    @ResponseBody
    public Result sentenceEvaluate(@RequestPart("audio") MultipartFile audio, @RequestParam("text") String text,
                                   @RequestParam("pinyin")String pinyin,@RequestParam("mode") String mode) {
        Result result = fileService.evaluate(audio, text, pinyin,mode);
        return result;
    }

    @GetMapping("details")
    public JSONObject details(@RequestParam String cpsgrpId){
        JSONObject result = fileService.getCorpusesByGroupId(cpsgrpId);
        return result;
    }

}
