package net.ecnu.controller;
import net.ecnu.model.Transliteration;
import net.ecnu.service.TransliterationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* 音译表(transliteration)表控制层
*
* @author lsy
*/
@RestController
@RequestMapping("/transliteration")
public class TransliterationController {
    /**
     * 服务对象
     */
    @Resource
    private TransliterationService transliterationService;


}
