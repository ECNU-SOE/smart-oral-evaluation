package net.ecnu.controller;

import net.ecnu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/test/v1")
public class EvaluateController {

    @Autowired
    FileService fileService;

    @PostMapping("upload")
    @ResponseBody
    public Result sentenceEvaluate(HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        MultipartFile audio = ((MultipartHttpServletRequest) request).getFile("audio");
        String text = params.getParameter("text");
        String pinyin = params.getParameter("pinyin");
        String mode = params.getParameter("mode");
        if (pinyin.isEmpty()){
            System.out.println("拼音为空");
        }
        Result result = fileService.evaluate(audio, text, pinyin,mode);
        return result;
    }

}
