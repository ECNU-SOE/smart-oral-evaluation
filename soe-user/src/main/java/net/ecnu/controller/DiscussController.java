package net.ecnu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.model.CourseDiscussDo;
import net.ecnu.model.dto.DiscussDto;
import net.ecnu.service.CourseDiscussService;
import net.ecnu.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @description:课程话题控制层
 * @Author lsy
 * @Date 2023/5/21 16:11
 */
@RestController
@RequestMapping("/api/discuss/v1")
public class DiscussController {

    @Autowired
    private CourseDiscussService courseDiscussService;

    /**
     * 新建话题
     * */
    @PostMapping("/addDiscuss")
    public JsonData addDiscuss(@RequestBody DiscussDto discussDto) {
        if (Objects.isNull(discussDto)) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        //新增话题，同时获取到该条数据的自增id
        if (courseDiscussService.addDiscuss(discussDto)) {
            return JsonData.buildSuccess("添加话题成功");
        } else {
            return JsonData.buildError("添加话题失败");
        }
    }

    /**
     * 回复
     * */
    @PostMapping("/reply")
    public JsonData replyDiscuss(@RequestBody DiscussDto discussDto) {
        if (Objects.isNull(discussDto)) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        courseDiscussService.reply(discussDto);
        return JsonData.buildSuccess();
    }

    /**
     * 查询班级下的讨论内容，不包含回复
     * TODO
     * 回复数还未统计，音频数据未返回
     * */
    @GetMapping("/getDiscussInfo")
    public JsonData getReplyInfo(@RequestParam("classId") String classId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        if(StringUtils.isEmpty(classId)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        if(Objects.isNull(pageNum))
            pageNum = 1;
        if(Objects.isNull(pageSize))
            pageSize = 10;
        Page<CourseDiscussDo> pageList = courseDiscussService.getDiscussInfo(classId,pageNum,pageSize);
        return JsonData.buildSuccess(pageList);
    }

    /**
     * 查询当前话题/回复下的回复
     * TODO
     * 回复数未统计,音频数据未返回
     * */
    @GetMapping("/getReplyInfoByCurrent")
    public JsonData getReplyInfoByCurrent(@RequestParam("discussId") String discussId,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        if(StringUtils.isEmpty(discussId)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        if(Objects.isNull(pageNum)){
            pageNum = 1;
        }
        if(Objects.isNull(pageSize)){
            pageSize = 10;
        }
        Page<CourseDiscussDo> pageList = courseDiscussService.getReplyInfo(discussId,pageNum,pageSize);
        return JsonData.buildSuccess(pageList);
    }

    /**
     * 点赞
     * */
    @GetMapping("/addLikes")
    public JsonData addLikes(@RequestParam String discussId){
        if (StringUtils.isEmpty(discussId)) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        if (courseDiscussService.addLikes(discussId) > 0) {
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildError("更新失败");
        }
    }
}
