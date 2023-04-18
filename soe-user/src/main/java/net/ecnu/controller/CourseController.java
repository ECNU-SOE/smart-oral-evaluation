package net.ecnu.controller;

import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.CourAddReq;
import net.ecnu.controller.request.CourFilterReq;
import net.ecnu.controller.request.CourUpdateReq;
import net.ecnu.model.common.PageData;
import net.ecnu.service.CourseService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cour/v1")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) CourAddReq courAddReq) {
        Object data = courseService.add(courAddReq);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("/del")
    public JsonData del(@RequestParam String id) {
        Object data = courseService.delete(id);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("detail")
    public JsonData detail(@RequestParam String id) {
        Object data = courseService.detail(id);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("update")
    public JsonData update(@RequestBody @Validated(Update.class) CourUpdateReq courUpdateReq) {
        Object data = courseService.update(courUpdateReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("list")
    public JsonData list(
            @RequestParam(value = "cur", defaultValue = "1") int cur,
            @RequestParam(value = "size", defaultValue = "50") int size,
            @RequestBody CourFilterReq courFilter) {
        Object data = courseService.pageByFilter(courFilter, new PageData(cur, size));
        return JsonData.buildSuccess(data);
    }
}
