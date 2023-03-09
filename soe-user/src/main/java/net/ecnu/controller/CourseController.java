package net.ecnu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.controller.request.CourseReq;
import net.ecnu.model.CourseDO;
import net.ecnu.service.CourseService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

//    @GetMapping("classes")
//    public JsonData getClasses(){
//        Object data = courseService.getClasses();
//        return JsonData.buildSuccess(data);
//    }

    @PostMapping("list")
    public JsonData getCourses(
            @RequestParam(value = "cur",defaultValue = "1") int cur,
            @RequestParam(value = "size",defaultValue = "50") int size,
            @RequestBody CourseFilterReq courseFilter){
        Object data = courseService.pageByFilter(courseFilter,new Page<>(cur,size));
        return JsonData.buildSuccess(data);
    }

}
