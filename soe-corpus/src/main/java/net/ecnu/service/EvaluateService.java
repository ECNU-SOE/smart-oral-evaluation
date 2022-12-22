package net.ecnu.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface EvaluateService {

    Object evaluate(File audio, String refText, String pinyin, long evalMode);

//    Result evaluate(MultipartFile file, String text, String pinyin, String mode);

    Object getCorpusesByGroupId(String cpsgrpId);

    File convert(MultipartFile audio);

    Object evaluateByXF(File convert, String refText, String pinyin, long evalMode);
}
