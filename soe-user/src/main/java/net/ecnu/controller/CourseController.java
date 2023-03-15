package net.ecnu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.controller.request.CourseCreateReq;
import net.ecnu.controller.request.CourseUpdateReq;
import net.ecnu.model.common.PageData;
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
    public JsonData create(@RequestBody @Validated(Create.class) CourseCreateReq courseCreateReq){
        Object data = courseService.create(courseCreateReq);
        return JsonData.buildSuccess(data);
    }

    @DeleteMapping("/delete/{id}")
    public JsonData delete(@PathVariable("id") String id){
        Object data = courseService.delete(id);
        return JsonData.buildSuccess(data);
    }

    @PutMapping("update")
    public JsonData update(@RequestBody CourseUpdateReq courseUpdateReq){
        Object data = courseService.update(courseUpdateReq);
        return JsonData.buildSuccess(data);
    }


    @PostMapping("list")
    public JsonData getCourses(
            @RequestParam(value = "cur",defaultValue = "1") int cur,
            @RequestParam(value = "size",defaultValue = "50") int size,
            @RequestBody CourseFilterReq courseFilter){
        Object data = courseService.pageByFilter(courseFilter,new PageData(cur,size));
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/info/{id}")
    public JsonData getCourse(@PathVariable("id") String id){
        Object data = courseService.getById(id);
        return JsonData.buildSuccess(data);
    }



}
