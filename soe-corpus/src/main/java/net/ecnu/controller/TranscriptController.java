package net.ecnu.controller;


import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.controller.request.CpsgrpReq;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.model.common.PageData;
import net.ecnu.model.dto.req.TranscriptInfoReq;
import net.ecnu.service.CpsgrpService;
import net.ecnu.service.TranscriptService;
import net.ecnu.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

    /**
     * 后台管理系统-获取答题报告列表
     * **/
    @PostMapping("/getTranscriptInfo")
    public JsonData getTranscriptInfo(@RequestBody TranscriptInfoReq transcriptInfoReq) {
        transcriptInfoReq.setPageNumber(Objects.isNull(transcriptInfoReq.getPageNumber()) ? 1 : transcriptInfoReq.getPageNumber());
        transcriptInfoReq.setPageSize(Objects.isNull(transcriptInfoReq.getPageSize()) ? 10 : transcriptInfoReq.getPageSize());
        PageData transcriptInfoResps = transcriptService.getTranscriptInfo(transcriptInfoReq);
        return JsonData.buildSuccess(transcriptInfoResps);
    }

    /**
     * 后台管理系统-删除答题报告
     * @param transcriptId 答题报告id
     * **/
    @GetMapping("/deleteTranscriptInfo")
    public JsonData deleteTranscriptInfo(@RequestParam("transcriptId") String transcriptId) {
        if (StringUtils.isEmpty(transcriptId)) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        if (transcriptService.deleteByPrimaryKey(transcriptId) <= 0) {
            throw new BizException(BizCodeEnum.TRANSCRIPT_ERROR.getCode(), "删除答题报告异常");
        }
        return JsonData.buildSuccess();
    }

    /**
     * 导出答题报告
     * **/
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody TranscriptInfoReq transcriptInfoReq) {

    }
}

