package net.ecnu.controller;


import net.ecnu.service.CommonService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/common/v1")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @GetMapping("languages")
    public JsonData languages() {
        Object data = commonService.listMotherTongue();
        return JsonData.buildSuccess(data);
    }
}

