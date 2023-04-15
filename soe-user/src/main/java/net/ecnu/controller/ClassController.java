package net.ecnu.controller;

import net.ecnu.controller.group.Create;
import net.ecnu.controller.request.ClassAddReq;
import net.ecnu.controller.request.ClassFilterReq;
import net.ecnu.controller.request.ClassUpdateReq;
import net.ecnu.controller.request.UsrClassAddReq;
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

    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) ClassAddReq classAddReq){
        Object data = classService.add(classAddReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("add_user_class")
    public JsonData add_user_cour(@RequestBody @Validated(Create.class) UsrClassAddReq usrClassAddReq){
//        System.out.println("这里的值为："+usrClassAddReq);
        Object data = classService.addUsrClass(usrClassAddReq);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/del/{id}")
    public JsonData del(@PathVariable("id") String id){
        Object data = classService.del(id);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/del_user_class/{id}")
    public JsonData del_user_class(@PathVariable("id") String id){
        Object data = classService.delUsrClass(id);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("update")
    public JsonData update(@RequestBody ClassUpdateReq classUpdateReq){
        Object data = classService.update(classUpdateReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("list")
    public JsonData list(
            @RequestParam(value = "cur",defaultValue = "1") int cur,
            @RequestParam(value = "size",defaultValue = "50") int size,
            @RequestBody ClassFilterReq classFilter){
        Object data = classService.pageByFilter(classFilter,new PageData(cur,size));
        return JsonData.buildSuccess(data);
    }

    @PostMapping("list_usr_class")
    public JsonData list_usr_class(@RequestBody UserClassDO userClassDO){
        Object data = classService.listUsrClass(userClassDO);
        return JsonData.buildSuccess(data);
    }
//
//    //发布考试/作业
//    @PostMapping("add_test")
//    public JsonData add_test(@RequestBody @Validated(Create.class) TestAddReq testAddReq){
//        Object data = courseService.addTest(testAddReq);
//        return JsonData.buildSuccess(data);
//    }
//
//    @PostMapping("update_test")
//    public JsonData update_test(@RequestBody @Validated(Update.class) TestUpdateReq testUpdateReq){
//        Object data = courseService.updateTest(testUpdateReq);
//        return JsonData.buildSuccess(data);
//    }
//
//    @GetMapping("/del_test/{id}")
//    public JsonData del_test(@PathVariable String id){
//        Object data = courseService.delTest(id);
//        return JsonData.buildSuccess(data);
//    }
//    @PostMapping("list_test")
//    public JsonData list_test(@RequestParam(value = "cur",defaultValue = "1") int cur,
//                                   @RequestParam(value = "size",defaultValue = "50") int size,
//                                   @RequestBody CpsgrpDO cpsgrpDO){
//        Object data = courseService.listTest(cpsgrpDO,new PageData(cur,size));
//        return JsonData.buildSuccess(data);
//    }
}
