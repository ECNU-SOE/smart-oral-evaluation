package net.ecnu.controller;

import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.*;
import net.ecnu.model.UserClassDO;
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
     * 查询用户所选班级列表
     * By:LYW
     */
    @GetMapping("list")
    public JsonData list(@RequestParam(required = false) String accountNo) {
        Object data = classService.listUserSelection(accountNo);
        return JsonData.buildSuccess(data);
    }
    @PostMapping("list_all")
    public JsonData listAll(@RequestParam(value = "cur", defaultValue = "1") int cur,
                            @RequestParam(value = "size", defaultValue = "50") int size,
                            @RequestBody ClassFilterReq classFilterReq){
        Object data = classService.listAllSelection(classFilterReq,new PageData(cur,size));
        return JsonData.buildSuccess(data);
    }


    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) ClassAddReq classAddReq) {
        Object data = classService.add(classAddReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("add_user_class")
    public JsonData add_user_class(@RequestBody @Validated(Create.class) UsrClassAddReq usrClassAddReq) {
        Object data = classService.addUsrClass(usrClassAddReq);
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
//        Object data = classService.listUserSelection(classFilterReq, new Page<ClassDO>(cur, size));
        return JsonData.buildSuccess(data);
    }

    /**
     * 查询登陆用户班级列表
     * Create By：TGX
     */
    @PostMapping("list_usr_class")
    public JsonData list_usr_class(@RequestBody UserClassDO userClassDO) {
        Object data = classService.listUsrClass(userClassDO);
        return JsonData.buildSuccess(data);
    }

    //发布考试/作业
    @PostMapping("add_test")
    public JsonData add_test(@RequestBody @Validated(Create.class) TestAddReq testAddReq) {
        Object data = classService.addTest(testAddReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("update_test")
    public JsonData update_test(@RequestBody @Validated(Update.class) TestUpdateReq testUpdateReq) {
        Object data = classService.updateTest(testUpdateReq);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/del_test")
    public JsonData del_test(@RequestParam String id) {
        Object data = classService.delTest(id);
        return JsonData.buildSuccess(data);
    }
}
