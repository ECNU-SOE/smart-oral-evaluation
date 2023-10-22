package net.ecnu.controller;
import net.ecnu.model.Transliteration;
import net.ecnu.model.vo.TransliterationVO;
import net.ecnu.service.TransliterationService;
import net.ecnu.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;

/**
* 音译表(transliteration)表控制层
*
* @author lsy
*/
@RestController
@RequestMapping("/api/transliteration/v1")
public class TransliterationController {
    /**
     * 服务对象
     */
    @Resource
    private TransliterationService transliterationService;

    @GetMapping("/getTransliterationInfo")
    public JsonData getTransliterationInfo(@RequestParam("audioText") String audioText){
        if (StringUtils.isEmpty(audioText)) {
            return JsonData.buildError("缺少音译文本");
        }
        TransliterationVO transliterationVO = transliterationService.getTransliterationInfo(audioText);
        return JsonData.buildSuccess(transliterationVO);
    }

}
