package net.ecnu.controller;


import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.TopicReq;
import net.ecnu.service.TopicService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic/v1")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * 查询topic下的所有子题
     */
    @GetMapping("detail")
    public JsonData getDetail(@RequestParam(value = "topicId", required = true) String topicId) {
        Object data = topicService.getDetail(topicId);
        return JsonData.buildSuccess(data);
    }

    /**
     * 添加大题目Topic
     */
    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) TopicReq topicReq) {
        Object data = topicService.add(topicReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 更新大题目Topic
     */
    @PostMapping("update")
    public JsonData update(@RequestBody @Validated(Update.class) TopicReq topicReq) {
        Object data = topicService.modify(topicReq);
        return JsonData.buildSuccess(data);
    }

    /**
     * 删除大题目Topic
     */
    @GetMapping("del")
    public JsonData del(@RequestParam(value = "topicId", required = true) String topicId) {
        Object data = topicService.del(topicId);
        return JsonData.buildSuccess(data);
    }

}

