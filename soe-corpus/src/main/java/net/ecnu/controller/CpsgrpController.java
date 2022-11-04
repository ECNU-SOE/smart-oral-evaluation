package net.ecnu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CorpusReq;
import net.ecnu.controller.request.CpsgrpCreateReq;
import net.ecnu.service.CorpusService;
import net.ecnu.service.CpsgrpService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cpsgrp/v1")
public class CpsgrpController {

    @Autowired
    private CpsgrpService cpsgrpService;
    /**
     * 查询语料组详情
     */
    @GetMapping("detail")
    public JsonData detail(@RequestParam String cpsgrpId) {
        Object data = cpsgrpService.detail(cpsgrpId);
        return JsonData.buildSuccess(data);
    }


    /**
     * 创建语料组（试卷，作业，测验等类型）
     */
    @PostMapping("create")
    public JsonData create(@RequestBody CpsgrpCreateReq cpsgrpCreateReq) {
        Object data = cpsgrpService.create(cpsgrpCreateReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 删除语料组（物理删除）
     */
    @GetMapping("del")
    public JsonData del(@RequestParam String cpsgrpId) {
        Object data = cpsgrpService.del(cpsgrpId);
        return JsonData.buildSuccess(data);
    }


}

