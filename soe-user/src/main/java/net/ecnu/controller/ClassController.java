package net.ecnu.controller;

import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.*;
import net.ecnu.model.common.PageData;
import net.ecnu.service.ClassService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/class/v1")
public class ClassController {

    @Autowired
    private ClassService classService;

    /**
     * 查询用户的班级列表
     * By:LYW
     */
    @GetMapping("list_sel")
    public JsonData listOne(@RequestParam(required = false) String accountNo) {
        Object data = classService.listSel(accountNo);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("list_mem")
    public JsonData listMem(@RequestParam(value = "cur", defaultValue = "1") int cur,
                            @RequestParam(value = "size", defaultValue = "50") int size,
                            @RequestBody UsrClassFilterReq usrClassFilter){
        Object data = classService.listMem(usrClassFilter, new PageData(cur, size));
        return JsonData.buildSuccess(data);
    }


    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) ClassAddReq classAddReq) {
        Object data = classService.add(classAddReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("add_user_class")
    public JsonData add_user_class(@RequestBody @Validated(Create.class) UsrClassReq usrClassReq) {
        Object data = classService.addUsrClass(usrClassReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("update_user_class")
    public JsonData update_user_class(@RequestBody @Validated(Update.class) UsrClassReq usrClassReq) {
        Object data = classService.updateUsrClass(usrClassReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("add_user_batch")
    public JsonData add_user_batch(@RequestBody @Validated(Create.class) UsrClassAddBatchReq usrClassAddBatchReq) {
        Object data = classService.addUsrClassBatch(usrClassAddBatchReq);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("del")
    public JsonData del(@RequestParam String id) {
        Object data = classService.del(id);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("detail")
    public JsonData detail(@RequestParam String id) {
        Object data = classService.detail(id);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/del_user_class")
    public JsonData del_user_class(@RequestParam String id) {
        Object data = classService.delUsrClass(id);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("update")
    public JsonData update(@RequestBody ClassUpdateReq classUpdateReq) {
        Object data = classService.update(classUpdateReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 查询班级列表
     * Create By：TGX
     * Update By：LYW
     */
    @PostMapping("list")
    public JsonData list(@RequestParam(value = "cur", defaultValue = "1") int cur,
                         @RequestParam(value = "size", defaultValue = "50") int size,
                         @RequestBody ClassFilterReq classFilterReq) {
        Object data = classService.pageByFilter(classFilterReq, new PageData(cur, size));
        return JsonData.buildSuccess(data);
    }

    //发布语料组到班级
    @PostMapping("add_cpsgrp")
    public JsonData add_cpsgrp(@RequestBody @Validated(Create.class) ClassCpsgrpReq classCpsgrpReq) {
        Object data = classService.addCpsgrp(classCpsgrpReq);
        return JsonData.buildSuccess(data);
    }
}
