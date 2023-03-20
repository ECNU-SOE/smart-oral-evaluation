package net.ecnu.controller;

import net.ecnu.controller.group.Create;
import net.ecnu.controller.request.*;
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
    public JsonData add_user_cour(@RequestBody UsrCourAddReq usrCourAddReq){
        Object data = userCourseService.add(usrCourAddReq);
        return JsonData.buildSuccess(data);
    }

    @DeleteMapping("/del/{id}")
    public JsonData del(@PathVariable("id") String id){
        Object data = courseService.delete(id);
        return JsonData.buildSuccess(data);
    }

    @DeleteMapping("/del_user_cour/{id}")
    public JsonData del_user_cour(@PathVariable("id") String id){
        Object data = userCourseService.delete(id);
        return JsonData.buildSuccess(data);
    }

    @PutMapping("update")
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
    public JsonData list_user_cour(
            @RequestParam(value = "cur",defaultValue = "1") int cur,
            @RequestParam(value = "size",defaultValue = "50") int size,
            @RequestBody UsrCourFilterReq userCourseFilter){
        Object data = userCourseService.pageByFilter(userCourseFilter,new PageData(cur,size));
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/info/{id}")
    public JsonData getCourse(@PathVariable("id") String id){
        Object data = courseService.getById(id);
        return JsonData.buildSuccess(data);
    }



}
