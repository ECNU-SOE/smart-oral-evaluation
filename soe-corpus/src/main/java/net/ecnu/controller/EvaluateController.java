package net.ecnu.controller;

import com.alibaba.fastjson.JSONObject;
import net.ecnu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/test/v1")
public class EvaluateController {

    @Autowired
    FileService fileService;

    /*@PostMapping("upload")
    @ResponseBody
    public Result sentenceEvaluate(HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);

        //audios   type    语料组id
        List<MultipartFile> audios = ((MultipartHttpServletRequest) request).getFiles("audios");
        String text = params.getParameter("text");
        String pinyin = params.getParameter("pinyin");
        String mode = params.getParameter("mode");
        Result result = fileService.evaluate(audio, text, pinyin,mode);
        return result;
    }*/

    @GetMapping("details")
    public List<JSONObject> details(@RequestParam String cpsgrpId){
        List<JSONObject> result = fileService.getCorpusesByGroupId(cpsgrpId);
        return result;
    }

}
