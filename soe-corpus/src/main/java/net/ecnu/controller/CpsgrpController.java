package net.ecnu.controller;


import net.ecnu.controller.request.CpsgrpCreateReq;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.model.common.PageData;
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
     * 获取题目组列表
     */
    @PostMapping("list")
    public JsonData list(@RequestParam(value = "cur", defaultValue = "1") int cur,
                         @RequestParam(value = "size", defaultValue = "50") int size,
                         @RequestBody CpsgrpFilterReq cpsgrpFilter) {
        Object data = cpsgrpService.pageByFilter(cpsgrpFilter, new PageData(cur, size));
        return JsonData.buildSuccess(data);
    }

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

    /**
     * 生成语料组 答题报告transcript
     */
    @PostMapping("transcript")
    public JsonData transcript(@RequestBody @Validated TranscriptReq transcriptReq) {
        Object data = cpsgrpService.genTranscript(transcriptReq);//生成报告
        return JsonData.buildSuccess(data);
    }


}

