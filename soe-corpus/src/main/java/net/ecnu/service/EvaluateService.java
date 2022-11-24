package net.ecnu.service;

import com.alibaba.fastjson.JSONObject;
import net.ecnu.controller.Result;
import org.springframework.web.multipart.MultipartFile;

public interface EvaluateService {
    Result evaluate(MultipartFile file,String text,String pinyin,String mode);

    JSONObject getCorpusesByGroupId(String cpsgrpId);
}
