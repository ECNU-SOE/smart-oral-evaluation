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
    public Result evaluate (@RequestPart("file") MultipartFile audio) throws Exception {
        Result result = fileService.sentenceEvaluete(audio);
        return result;
    }

}
