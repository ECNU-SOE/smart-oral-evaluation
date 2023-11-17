package net.ecnu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.controller.request.CpsrcdReq;
import net.ecnu.controller.request.TopicReq;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.service.CpsrcdService;
import net.ecnu.service.TopicService;
import net.ecnu.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/cpsrcd/v1")
public class CpsrcdController {

    @Autowired
    private CpsrcdService cpsrcdService;

    /**
     * 添加语料原型
     */
    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) CpsrcdReq cpsrcdReq) {
        Object data = cpsrcdService.add(cpsrcdReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 查询语料列表cpsrcd
     */
    @PostMapping("list")
    public JsonData list(@RequestParam(value = "cur", defaultValue = "1") int cur,
                         @RequestParam(value = "size", defaultValue = "50") int size,
                         @RequestBody CpsrcdFilterReq cpsrcdFilter) {
        Object data = cpsrcdService.pageByFilter(cpsrcdFilter, new Page<>(cur, size));
        return JsonData.buildSuccess(data);
    }

    /**
     * 更新语料原型cpsrcd
     */
    @PostMapping("mod")
    public JsonData mod(@RequestBody @Validated(Update.class) CpsrcdReq cpsrcdReq) {
        Object data = cpsrcdService.mod(cpsrcdReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 删除语料原型cpsrcd
     */
    @GetMapping("del")
    public JsonData del(@RequestParam(value = "cpsrcdId", required = true) String cpsrcdId) {
        Object data = cpsrcdService.del(cpsrcdId);
        return JsonData.buildSuccess(data);
    }

    /**
     * 查询语料详情
     */
    @GetMapping("/getCpsrcdDetail")
    public JsonData getCpsrcdDetail(@RequestParam("cpsrcdId") String cpsrcdId) {
        if (StringUtils.isEmpty(cpsrcdId)) {
            return JsonData.buildError("语料id不能为空");
        }
        Object o = cpsrcdService.getCpsrcdDetail(cpsrcdId);
        return JsonData.buildSuccess(o);
    }

    /**
     * 随机一题
     */
    @PostMapping("rand")
    public JsonData rand(@RequestBody CpsrcdFilterReq cpsrcdFilter) {
        long startTime = new Date().getTime();
        Object data = cpsrcdService.random(cpsrcdFilter);
        long endTime = new Date().getTime();
        System.out.println("随机一题总耗时："+(endTime-startTime)+"ms");
        return JsonData.buildSuccess(data);
    }
}

