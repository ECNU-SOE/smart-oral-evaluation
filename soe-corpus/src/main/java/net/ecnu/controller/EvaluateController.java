package net.ecnu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import net.ecnu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/test/v1")
public class EvaluateController {

    @Autowired
    FileService fileService;

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
