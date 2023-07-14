package net.ecnu.controller;


import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.controller.request.CpsgrpReq;
import net.ecnu.controller.request.CpsrcdTagReq;
import net.ecnu.controller.request.TagReq;
import net.ecnu.model.common.PageData;
import net.ecnu.service.TagService;
import net.ecnu.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYW
 * @since 2023-07-14
 */
@RestController
@RequestMapping("/api/tag/v1")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("create")
    public JsonData create(@RequestBody @Validated(Create.class)TagReq tagReq) {
        Object data = tagService.create(tagReq);
        return JsonData.buildSuccess(data);
    }

    @GetMapping("del")
    public JsonData del(@RequestParam Integer id) {
        Object data = tagService.delete(id);
        return JsonData.buildSuccess(data);
    }
    @PostMapping("update")
    public JsonData update(@RequestBody @Validated(Update.class)TagReq tagReq) {
        Object data = tagService.update(tagReq);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("list")
    public JsonData list(@RequestParam(value = "cur", defaultValue = "1") int cur,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         @RequestBody TagReq tagReq) {
        Object data = tagService.list(tagReq, new PageData(cur, size));
        return JsonData.buildSuccess(data);
    }

    @PostMapping("add_tag")
    public JsonData addTag(@RequestBody CpsrcdTagReq cpsrcdTagReq) {
        Object data = tagService.addTag(cpsrcdTagReq);
        return JsonData.buildSuccess(data);
    }
}

