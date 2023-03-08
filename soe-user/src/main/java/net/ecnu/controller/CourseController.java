package net.ecnu.controller;

import net.ecnu.controller.group.Create;
import net.ecnu.controller.request.CourseReq;
import net.ecnu.service.CourseService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/course/v1")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @PostMapping("create")
    public JsonData create(@RequestBody @Validated(Create.class) CourseReq courseReq){
        Object data = courseService.create(courseReq);
        return JsonData.buildSuccess(data);
    }

    @DeleteMapping("delete")
    public JsonData delete(@RequestBody CourseReq courseReq){
        Object data = courseService.delete(courseReq);
        return JsonData.buildSuccess(data);
    }

    @PutMapping("update")
    public JsonData update(@RequestBody CourseReq courseReq){
        Object data = courseService.update(courseReq);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("classes")
    public JsonData getClasses(){
        Object data = courseService.getClasses();
        return JsonData.buildSuccess(data);
    }

    @GetMapping("courses")
    public JsonData getCourses(@RequestBody CourseReq courseReq){
        Object data = courseService.getCourses(courseReq);
        return JsonData.buildSuccess(data);
    }

}
