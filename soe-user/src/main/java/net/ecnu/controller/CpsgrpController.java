package net.ecnu.controller;


import net.ecnu.service.CpsgrpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 语料组(作业、试卷、测验) 前端控制器
 * </p>
 *
 * @author TGX
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/api/quiz/v1")
public class CpsgrpController {

    @Autowired
    private CpsgrpService cpsgrpService;


}

