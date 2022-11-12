package net.ecnu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import net.ecnu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/test/v1")
public class EvaluateController {

    @Autowired
    FileService fileService;

    @PostMapping("upload")
    @ResponseBody
    public Result sentenceEvaluate(@RequestBody List<JSONObject> request) {
        Result result = new Result();
        for(int i=0;i<request.size();i++){
            int type =(int)request.get(i).get("type");
            MultipartFile audio =(MultipartFile)request.get(i).get("audio");
            String text = null;
            String pinyin =null;
            List<JSONObject> refText =(List<JSONObject>)request.get(i).get("refText");
            for(int j=0;j<refText.size();j++){
                text = (String)refText.get(j).get("word")+"";
                pinyin = (String)refText.get(j).get("pinyin")+"";
            }
            result = fileService.evaluate(audio, text.trim(), pinyin.trim(), type);
        }
        return result;
    }

    @GetMapping("details")
    public JSONObject details(@RequestParam String cpsgrpId){
        JSONObject result = fileService.getCorpusesByGroupId(cpsgrpId);
        return result;
    }

}
