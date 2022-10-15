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

    @PostMapping("uploadSentence")
    public Result sentenceEvaluate (@RequestPart("audio") MultipartFile audio) throws Exception {
        Result result = fileService.sentenceEvaluete(audio);
        return result;
    }
    @PostMapping("uploadWord")
    public Result wordEvaluate (@RequestPart("audio") MultipartFile audio) throws Exception {
        Result result = fileService.wordEvaluate(audio);
        return result;
    }
    @PostMapping("uploadChapter")
    public Result chapterEvaluate (@RequestPart("audio") MultipartFile audio) throws Exception {
        Result result = fileService.chapterEvaluate(audio);
        return result;
    }

}
