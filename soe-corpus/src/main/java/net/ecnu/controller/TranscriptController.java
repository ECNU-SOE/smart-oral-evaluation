package net.ecnu.controller;


import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.controller.request.CpsgrpReq;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.model.common.PageData;
import net.ecnu.service.CpsgrpService;
import net.ecnu.service.TranscriptService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transcript/v1")
public class TranscriptController {


    @Autowired
    private TranscriptService transcriptService;

    /**
     * 存储答题题报告
     */
    @PostMapping("save")
    public JsonData saveTranscript(@RequestBody @Validated(Create.class) TranscriptReq transcriptReq) {
        Object data = transcriptService.save(transcriptReq);//生成报告
        return JsonData.buildSuccess(data);
    }

    /**
     * 查询答题报告
     */
    @PostMapping("list")
    public JsonData transcript(@RequestBody TranscriptReq transcriptReq) {
        Object data = transcriptService.getTranscript(transcriptReq);
        return JsonData.buildSuccess(data);
    }


}

