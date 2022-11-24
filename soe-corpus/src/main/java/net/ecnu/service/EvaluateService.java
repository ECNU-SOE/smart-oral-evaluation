package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CorpusReq;
import net.ecnu.model.CorpusDO;
import org.springframework.web.multipart.MultipartFile;

public interface EvaluateService {

    Object evaluate(MultipartFile audio, String refText, String pinyin, long evalMode);
}
