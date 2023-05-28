package net.ecnu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.model.ClassDiscussDo;
import net.ecnu.model.dto.DiscussDto;
import net.ecnu.model.dto.ForwardDto;
import net.ecnu.model.vo.DiscussVo;
import net.ecnu.model.vo.ReplyInfoVo;
import net.ecnu.service.ClassDiscussService;
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
    private ClassDiscussService classDiscussService;

    /**
     * 新建话题
     * */
    @PostMapping("/addDiscuss")
    public JsonData addDiscuss(@RequestBody DiscussDto discussDto) {
        if (Objects.isNull(discussDto)) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        //新增话题，同时获取到该条数据的自增id
        if (classDiscussService.addDiscuss(discussDto)) {
            return JsonData.buildSuccess(null,"添加话题成功");
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
        classDiscussService.reply(discussDto);
        return JsonData.buildSuccess();
    }

    /**
     * 查询班级下的讨论内容，不包含回复
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
        Page<DiscussVo> pageList = classDiscussService.getDiscussInfo(classId,pageNum,pageSize);
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
        Page<ReplyInfoVo> pageList = classDiscussService.getReplyInfo(discussId,pageNum,pageSize);
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
        if (classDiscussService.addLikes(discussId) > 0) {
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildError("更新失败");
        }
    }

    /**
     * 转发
     * */
    @PostMapping("/forward")
    public JsonData forward(@RequestBody ForwardDto forwardDto){
        if (StringUtils.isEmpty(forwardDto.getClassId()) || StringUtils.isEmpty(forwardDto.getDiscussTest())
                || Objects.isNull(forwardDto.getForwardId())) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        if (classDiscussService.forward(forwardDto) > 0) {
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildError("转发失败");
        }
    }

    /**
     * 置顶话题
     * */
    @GetMapping("/topDiscuss")
    public JsonData topDiscuss(@RequestParam("discussId")Long discussId){
        if (Objects.isNull(discussId)) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        if (classDiscussService.topDiscuss(discussId) <= 0) {
            return JsonData.buildSuccess(null, "置顶成功");
        } else {
            return JsonData.buildError("置顶失败");
        }
    }
}
