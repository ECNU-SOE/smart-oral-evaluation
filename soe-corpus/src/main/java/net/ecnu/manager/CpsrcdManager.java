package net.ecnu.manager;


import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;

import java.util.List;

public interface CpsrcdManager {

    List<CpsrcdDO> listByCpsgrpId(String cpsgrpId);
}
