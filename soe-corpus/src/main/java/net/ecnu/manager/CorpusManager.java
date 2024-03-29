package net.ecnu.manager;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CorpusReq;
import net.ecnu.model.CorpusDO;

public interface CorpusManager {

    IPage<CorpusDO> pageByFilter(CorpusFilterReq corpusFilter, Page<CorpusDO> corpusDOPage);

    void add(CorpusDO corpusDO);

    void delCorpusInfo(String corpusId);

    void updateCorpusInfo(CorpusReq corpusReq);

}
