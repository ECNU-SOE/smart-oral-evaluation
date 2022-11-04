package net.ecnu.manager;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.model.CorpusDO;
import net.ecnu.model.CpsgrpDO;

public interface CpsgrpManager {

    int insert(CpsgrpDO cpsgrpDO);
}
