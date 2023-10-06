package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CorpusReq;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.model.CorpusDO;
import net.ecnu.model.CpsrcdDO;

public interface CorpusService {

    Object pageByFilter(CorpusFilterReq corpusFilter, Page<CorpusDO> objectPage);

    void add(CorpusReq corpusReq);

    void delCorpusInfo(String corpusId);

    void updateCorpusInfo(CorpusReq corpusReq);

}
