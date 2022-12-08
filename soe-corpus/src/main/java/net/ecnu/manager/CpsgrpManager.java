package net.ecnu.manager;


import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.CpsgrpVO;

import java.util.List;


public interface CpsgrpManager {

    int insert(CpsgrpDO cpsgrpDO);

    List<CpsgrpDO> listByFilter(CpsgrpFilterReq cpsgrpFilter, PageData pageData);

    int countByFilter(CpsgrpFilterReq cpsgrpFilter);
}
