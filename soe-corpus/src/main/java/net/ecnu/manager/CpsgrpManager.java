package net.ecnu.manager;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.model.CorpusDO;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.VO.CpsgrpVO;

import java.util.List;

public interface CpsgrpManager {

    int insert(CpsgrpDO cpsgrpDO);

    List<CpsgrpVO> listByFilter(CpsgrpFilterReq cpsgrpFilter);
}
