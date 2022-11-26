package net.ecnu.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface EvaluateService {

    Object evaluate(MultipartFile audio, String refText, String pinyin, long evalMode);

//    Result evaluate(MultipartFile file, String text, String pinyin, String mode);

    JSONObject getCorpusesByGroupId(String cpsgrpId);

    File convert(MultipartFile audio);
}