package net.ecnu.controller;

import net.ecnu.controller.group.Create;
import net.ecnu.controller.request.CourseReq;
import net.ecnu.service.CourseService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
