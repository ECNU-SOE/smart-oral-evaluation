package net.ecnu.controller;


import net.ecnu.service.FileService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/corpus/v1")
public class EvaluateController {

    @Autowired
    private FileService fileService;

    @PostMapping("evaluate")
    public JsonData upload(@RequestPart("file") MultipartFile file) {
        fileService.evaluate();
        return null;
    }
}

