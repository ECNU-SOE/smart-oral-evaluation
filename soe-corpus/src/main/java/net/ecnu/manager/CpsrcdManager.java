package net.ecnu.manager;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;

import java.util.List;

public interface CpsrcdManager {

    List<CpsrcdDO> listByCpsgrpId(String cpsgrpId);

    int countByCpsgrpId(String cpsgrpId);

    IPage<CpsrcdDO> pageByFilter(CpsrcdFilterReq cpsrcdFilter, Page<CpsrcdDO> cpsrcdDOPage);

    List<CpsrcdDO> getCpsrcdDOs(CpsrcdFilterReq cpsrcdFilterReq);

    List<String> getCpsrcdIdsByTagIds(List<Integer> tagIds);

}
