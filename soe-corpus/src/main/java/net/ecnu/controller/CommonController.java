package net.ecnu.controller;


import net.ecnu.service.CommonService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/common/v1")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @GetMapping("languages")
    public JsonData languages() throws IOException {
        Object data = commonService.listMotherTongue();
        return JsonData.buildSuccess(data);
    }

    @GetMapping("languages2")
    public JsonData languages2() throws IOException {
        Object data = commonService.listMotherTongue2();
        return JsonData.buildSuccess(data);
    }

    @GetMapping("paper")
    public JsonData paper(@RequestParam(value = "token", required = false) String token,
                          @RequestParam(value = "languageId", required = true) int languageId) {
        Object data = commonService.getCpsgrpIdByFirstLanguageId(languageId);
        return JsonData.buildSuccess("cpsgrp_1588871928125460480");
    }

}

