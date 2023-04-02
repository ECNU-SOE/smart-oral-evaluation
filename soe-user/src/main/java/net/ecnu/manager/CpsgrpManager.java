package net.ecnu.manager;

import net.ecnu.controller.request.CourFilterReq;
import net.ecnu.model.CourseDO;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.CpsgrpVO;

import java.util.List;

public interface CpsgrpManager {
    List<CpsgrpDO> pageByFilter(CpsgrpDO cpsgrpDO, PageData pageData);

    int countByFilter(CpsgrpDO cpsgrpDO);
}
