package net.ecnu.controller;


import net.ecnu.service.EvaluateService;
import net.ecnu.service.OssService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/file/v1")
public class FileController {

    @Autowired
    private OssService ossService;

    @Autowired
    private EvaluateService evaluateService;

    /**
     * 音频上传
     */
    @PostMapping("upload")
    public JsonData upload(@RequestPart("file") MultipartFile file) {
        //音频格式转换
//        File convertAudio = evaluateService.convert_lyw(file);

        Object data = ossService.uploadAudio(file);
        return JsonData.buildSuccess(data);
    }
}

