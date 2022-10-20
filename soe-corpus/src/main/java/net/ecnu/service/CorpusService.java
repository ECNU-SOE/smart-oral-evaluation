package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.model.CorpusDO;
import org.springframework.web.multipart.MultipartFile;

public interface CorpusService {
    Object pageByFilter(CorpusFilterReq corpusFilter, Page<CorpusDO> objectPage);
}
