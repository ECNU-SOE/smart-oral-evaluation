package net.ecnu.controller;

import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.*;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.UserCourseDO;
import net.ecnu.model.common.PageData;
import net.ecnu.service.CourseService;
import net.ecnu.service.UserCourseService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/course/v1")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserCourseService userCourseService;


    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) CourAddReq courAddReq){
        Object data = courseService.add(courAddReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("add_user_cour")
    public JsonData add_user_cour(@RequestBody @Validated(Create.class) UsrCourAddReq usrCourAddReq){
        Object data = userCourseService.add(usrCourAddReq);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/del/{id}")
    public JsonData del(@PathVariable("id") String id){
        Object data = courseService.delete(id);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/del_user_cour/{id}")
    public JsonData del_user_cour(@PathVariable("id") String id){
        Object data = userCourseService.delete(id);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("update")
    public JsonData update(@RequestBody CourUpdateReq courUpdateReq){
        Object data = courseService.update(courUpdateReq);
        return JsonData.buildSuccess(data);
    }


    @PostMapping("list")
    public JsonData list(
            @RequestParam(value = "cur",defaultValue = "1") int cur,
            @RequestParam(value = "size",defaultValue = "50") int size,
            @RequestBody CourFilterReq courseFilter){
        Object data = courseService.pageByFilter(courseFilter,new PageData(cur,size));
        return JsonData.buildSuccess(data);
    }

    @PostMapping("list_user_cour")
    public JsonData list_user_cour(@RequestBody UserCourseDO userCourseDO){
        Object data = userCourseService.list_user_cour(userCourseDO);
        return JsonData.buildSuccess(data);
    }

    //发布考试/作业
    @PostMapping("add_test")
    public JsonData add_test(@RequestBody @Validated(Create.class) TestAddReq testAddReq){
        Object data = courseService.addTest(testAddReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("update_test")
    public JsonData update_test(@RequestBody @Validated(Update.class) TestUpdateReq testUpdateReq){
        Object data = courseService.updateTest(testUpdateReq);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/del_test/{id}")
    public JsonData del_test(@PathVariable String id){
        Object data = courseService.delTest(id);
        return JsonData.buildSuccess(data);
    }
    @PostMapping("list_test")
    public JsonData list_test(@RequestParam(value = "cur",defaultValue = "1") int cur,
                                   @RequestParam(value = "size",defaultValue = "50") int size,
                                   @RequestBody CpsgrpDO cpsgrpDO){
        Object data = courseService.listTest(cpsgrpDO,new PageData(cur,size));
        return JsonData.buildSuccess(data);
    }
}
