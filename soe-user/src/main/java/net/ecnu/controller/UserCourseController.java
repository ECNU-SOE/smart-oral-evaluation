package net.ecnu.controller;

import net.ecnu.controller.group.Create;
import net.ecnu.controller.request.CourseCreateReq;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.controller.request.UserCourseCreateReq;
import net.ecnu.controller.request.UserCourseFilterReq;
import net.ecnu.model.UserCourseDO;
import net.ecnu.model.common.PageData;
import net.ecnu.service.UserCourseService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user_course/v1")
public class UserCourseController {

    @Autowired
    private UserCourseService userCourseService;

    @PostMapping("create")
    public JsonData create(@RequestBody UserCourseCreateReq userCourseCreateReq){
        Object data = userCourseService.create(userCourseCreateReq);
        return JsonData.buildSuccess(data);
    }

    @DeleteMapping("/delete/{id}")
    public JsonData delete(@PathVariable("id") String id){
        Object data = userCourseService.delete(id);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("list")
    public JsonData list(
            @RequestParam(value = "cur",defaultValue = "1") int cur,
            @RequestParam(value = "size",defaultValue = "50") int size,
            @RequestBody UserCourseFilterReq userCourseFilter){
        Object data = userCourseService.pageByFilter(userCourseFilter,new PageData(cur,size));
        return JsonData.buildSuccess(data);
    }
}
