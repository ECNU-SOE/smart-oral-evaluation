package net.ecnu.controller;


import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Delete;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.TopicCpsReq;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.service.TopicCpsService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic_cps/v1")
public class TopicCpsController {

    @Autowired
    private TopicCpsService topicCpsService;

    /**
     * 添加子题
     **/
    @PostMapping("add")
    public JsonData add(@RequestBody @Validated(Create.class) TopicCpsReq topicCpsReq) {
        CpsrcdVO cpsrcdVO = topicCpsService.addOne(topicCpsReq);
        return JsonData.buildSuccess(cpsrcdVO);
    }

    /**
     * 删除子题
     */
    @PostMapping("del")
    public JsonData del(@RequestBody @Validated(Delete.class) TopicCpsReq topicCpsReq) {
        int rows = topicCpsService.delOne(topicCpsReq);
        return JsonData.buildSuccess(rows);
    }

    /**
     * 更新子题
     */
    @PostMapping("update")
    public JsonData update(@RequestBody @Validated(Update.class) TopicCpsReq topicCpsReq) {
        CpsrcdVO cpsrcdVO = topicCpsService.updateOne(topicCpsReq);
        return JsonData.buildSuccess(cpsrcdVO);
    }


}

