package net.ecnu.controller;

import net.ecnu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/test/v1")
public class EvaluateController {

    @Autowired
    FileService fileService;

    @PostMapping("upload")
    public Result sentenceEvaluate (@RequestPart("audio") MultipartFile audio,@RequestPart("text") String text) throws Exception {
        Result result = fileService.evaluate(audio,text);
        return result;
    }

}
