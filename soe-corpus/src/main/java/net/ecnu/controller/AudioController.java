package net.ecnu.controller;


import net.ecnu.service.OssService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/audio/v1")
public class AudioController {

    @Autowired
    private OssService ossService;

    /**
     * 音频上传
     */
    @PostMapping("upload")
    public JsonData upload(@RequestPart("file") MultipartFile file) {
        Object data = ossService.uploadAudio(file);
        return JsonData.buildSuccess(data);
    }
}

