package net.ecnu.controller;


import net.ecnu.service.FileService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/record/v1")
public class RecordController {

    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    public JsonData upload(@RequestPart("file") MultipartFile file) {
        return JsonData.buildSuccess(fileService.uploadVoiceRecord(file));
    }
}

