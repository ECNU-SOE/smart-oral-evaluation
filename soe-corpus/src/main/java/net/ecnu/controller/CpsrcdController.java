package net.ecnu.controller;


import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.CpsrcdReq;
import net.ecnu.controller.request.TopicReq;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.service.CpsrcdService;
import net.ecnu.service.TopicService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cpsrcd/v1")
public class CpsrcdController {

    @Autowired
    private CpsrcdService cpsrcdService;

    /**
     * 向语料组cpsgrp中添加子题cpsrcd
     */
    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) CpsrcdReq cpsrcdReq) {
        Object data = cpsrcdService.add(cpsrcdReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 更新语料组cpsgrp中的子题cpsrcd
     */
    @PostMapping("mod")
    public JsonData mod(@RequestBody @Validated(Update.class) CpsrcdReq cpsrcdReq) {
        Object data = cpsrcdService.mod(cpsrcdReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 删除子题cpsrcd
     */
    @GetMapping("del")
    public JsonData del(@RequestParam(value = "cpsrcdId", required = true) String cpsrcdId) {
        Object data = cpsrcdService.del(cpsrcdId);
        return JsonData.buildSuccess(data);
    }

}

