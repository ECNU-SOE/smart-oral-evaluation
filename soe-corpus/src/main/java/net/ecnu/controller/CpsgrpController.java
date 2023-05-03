package net.ecnu.controller;


import net.ecnu.aop.annotate.TokenAnalysis;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.CpsgrpReq;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.model.common.PageData;
import net.ecnu.service.CpsgrpService;
import net.ecnu.service.TranscriptService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cpsgrp/v1")
public class CpsgrpController {

    @Autowired
    private CpsgrpService cpsgrpService;

    @Autowired
    private TranscriptService transcriptService;

    /**
     * 查询语料组列表
     */
    @PostMapping("list")
    public JsonData list(@RequestParam(value = "cur", defaultValue = "1") int cur,
                         @RequestParam(value = "size", defaultValue = "10") int size,
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
     * 创建语料组cpsgrp
     */
    @TokenAnalysis
    @PostMapping("create")
    public JsonData create(@RequestBody @Validated(Create.class) CpsgrpReq cpsgrpReq) {
        Object data = cpsgrpService.create(cpsgrpReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 更新语料组cpsgrp
     */
    @PostMapping("update")
    public JsonData update(@RequestBody @Validated(Update.class) CpsgrpReq cpsgrpReq) {
        Object data = cpsgrpService.update(cpsgrpReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 删除语料组（逻辑删除）
     */
    @GetMapping("del")
    public JsonData del(@RequestParam String cpsgrpId) {
        Object data = cpsgrpService.del(cpsgrpId);
        return JsonData.buildSuccess(data);
    }

//    /**
//     * 生成语料组 答题报告transcript
//     */
//    @Deprecated
//    @PostMapping("transcript")
//    public JsonData transcript(@RequestBody @Validated TranscriptReq transcriptReq) {
//        Object data = cpsgrpService.genTranscript(transcriptReq);//生成报告
//        return JsonData.buildSuccess(data);
//    }

    /**
     * 存储答题题报告
     */
    @TokenAnalysis
    @PostMapping("save_transcript")
    public JsonData saveTranscript(@RequestBody @Validated TranscriptReq transcriptReq) {
        Object data = transcriptService.save(transcriptReq);//生成报告
        return JsonData.buildSuccess(data);
    }

    /**
     * 查询答题报告
     */
    @TokenAnalysis
    @PostMapping("transcripts")
    public JsonData transcript(@RequestBody TranscriptReq transcriptReq) {
        Object data = transcriptService.getTranscript(transcriptReq);//生成报告
        return JsonData.buildSuccess(data);
    }


}

