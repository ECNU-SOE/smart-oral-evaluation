package net.ecnu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CorpusReq;
import net.ecnu.service.CorpusService;
import net.ecnu.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/corpus/v1")
public class CorpusController {

    @Autowired
    private CorpusService corpusService;


    /**
     * 获取公共语料列表（分页）
     */
    @PostMapping("list")
    public JsonData list(@RequestParam(value = "cur", defaultValue = "1") int cur,
                         @RequestParam(value = "size", defaultValue = "50") int size,
                         @RequestBody CorpusFilterReq corpusFilter) {
        Object data = corpusService.pageByFilter(corpusFilter, new Page<>(cur, size));
        return JsonData.buildSuccess(data);
    }

    /**
     * 添加语料
     */
    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) CorpusReq corpusReq) {
        if (StringUtils.isEmpty(corpusReq.getRefText())) {
            return JsonData.buildError("语料文本为必填！");
        }
        corpusService.add(corpusReq);
        return JsonData.buildSuccess("添加语料成功！");
    }

    /**
     * 删除语料
     */
    @GetMapping("/del")
    public JsonData delCorpusInfo(@RequestParam String corpusId) {
        if (StringUtils.isEmpty(corpusId)) {
            return JsonData.buildError("语料id为空");
        }
        corpusService.delCorpusInfo(corpusId);
        return JsonData.buildSuccess("删除语料成功！");
    }

    /**
     * 更新语料
     */
    @PostMapping("/update")
    public JsonData updateCorpusInfo(@RequestBody CorpusReq corpusReq) {
        if(Objects.isNull(corpusReq)){
            return JsonData.buildError("更新语料内容不能为空！");
        }
        corpusService.updateCorpusInfo(corpusReq);
        return JsonData.buildSuccess("更新语料成功！");
    }

    @GetMapping("rand")
    public JsonData rand() {
        Object data = corpusService.random();
        return JsonData.buildSuccess(data);
    }

}

