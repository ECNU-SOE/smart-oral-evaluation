package net.ecnu.controller;


import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Delete;
import net.ecnu.controller.group.Update;
import net.ecnu.controller.request.TagFilterReq;
import net.ecnu.controller.request.TaggingReq;
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
                         @RequestBody TagFilterReq tagFilterReq) {
        Object data = tagService.list(tagFilterReq, new PageData(cur, size));
        return JsonData.buildSuccess(data);
    }

    @PostMapping("add_tagging")
    public JsonData addTagging(@RequestBody TaggingReq taggingReq) {
        Object data = tagService.addTagging(taggingReq);
        return JsonData.buildSuccess(data);
    }
    @PostMapping("list_ent_tags")
    public JsonData listEntityTags(@RequestParam String entityId) {
        Object data = tagService.listEntityTags(entityId);
        return JsonData.buildSuccess(data);
    }

    @PostMapping("del_tagging")
    public JsonData delTagging(@RequestBody @Validated(Delete.class) TaggingReq taggingReq) {
        Object data = tagService.delTagging(taggingReq);
        return JsonData.buildSuccess(data);
    }
}

