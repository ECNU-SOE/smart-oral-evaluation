package net.ecnu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.service.CorpusService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}

