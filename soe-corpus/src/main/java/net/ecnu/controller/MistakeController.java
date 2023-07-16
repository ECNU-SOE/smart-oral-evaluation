package net.ecnu.controller;

import lombok.extern.slf4j.Slf4j;
import net.ecnu.model.dto.MistakeAnswerDto;
import net.ecnu.model.dto.MistakesDto;
import net.ecnu.model.vo.MistakeAnswerVO;
import net.ecnu.model.vo.MistakeDetailVO;
import net.ecnu.model.vo.MistakesVO;
import net.ecnu.service.MistakeAudioService;
import net.ecnu.util.JsonData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @description:错题模块
 * @Author lsy
 * @Date 2023/7/15 15:26
 */
@Slf4j
@RestController
@RequestMapping("/api/mistake/v1")
public class MistakeController {

    @Resource
    private MistakeAudioService mistakeAudioService;

    /**
     * 错题本查询接口
     */
    @GetMapping("/getDetail")
    public JsonData getDetail(@RequestParam("oneWeekKey") Integer oneWeekKey) {
        if (Objects.isNull(oneWeekKey)) {
            return JsonData.buildError("缺少参数oneWeekKey");
        }
        MistakeDetailVO mistakeDetailVO = mistakeAudioService.getDetail(oneWeekKey);
        return JsonData.buildSuccess(mistakeDetailVO);
    }

    /**
     * 错题回顾接口
     * **/
    @PostMapping("/getMistakes")
    public JsonData getMistake(@RequestBody MistakesDto mistakesDto){
        if (Objects.isNull(mistakesDto.getMistakeTypeCode())) {
            return JsonData.buildError("缺少题目类型参数");
        }
        if (Objects.isNull(mistakesDto.getOneWeekKey())) {
            return JsonData.buildError("缺少是否获取近一周数据参数");
        }
        List<MistakesVO> mistakesVOList = mistakeAudioService.getMistake(mistakesDto);
        return JsonData.buildSuccess(mistakesVOList);
    }

    /**
     * 答题接口
     * **/
    @PostMapping("/answer")
    public JsonData answer(@RequestBody MistakeAnswerDto mistakeAnswer){
        MistakeAnswerVO mistakeAnswerVO = mistakeAudioService.checkAnswer(mistakeAnswer);
        return JsonData.buildSuccess(mistakeAnswerVO);
    }

}
