package net.ecnu.controller;

import net.ecnu.util.JsonData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transcript/v1")
public class TranscriptController {

    @PostMapping("save")
    public JsonData create(){

        return null;
    }
}
