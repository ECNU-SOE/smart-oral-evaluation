package net.ecnu.service;

import org.springframework.web.multipart.MultipartFile;

public interface EvaluateService {

    Object evaluate(MultipartFile audio, String refText, String pinyin, long evalMode);

//    Result evaluate(MultipartFile file, String text, String pinyin, String mode);

    Object getCorpusesByGroupId(String cpsgrpId);

}
