package net.ecnu.manager;


import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.vo.CpsgrpVO;

import java.util.List;


public interface CpsgrpManager {

    int insert(CpsgrpDO cpsgrpDO);

    List<CpsgrpVO> listByFilter(CpsgrpFilterReq cpsgrpFilter);

}
