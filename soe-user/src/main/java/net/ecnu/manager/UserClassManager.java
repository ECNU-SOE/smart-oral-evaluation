package net.ecnu.manager;

import net.ecnu.controller.request.UserFilterReq;
import net.ecnu.controller.request.UsrClassFilterReq;
import net.ecnu.model.UserClassDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface UserClassManager {
    UserClassDO getByAccountNoAndClassId(String accountNo, String courseId);
}
