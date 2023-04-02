package net.ecnu.service;

import net.ecnu.controller.request.*;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.common.PageData;

public interface CourseService {
    Object add(CourAddReq courAddReq);
    Object delete(String id);
    Object update(CourUpdateReq courUpdateReq);
    Object pageByFilter(CourFilterReq courseFilter, PageData pageData);
    Object addTest(TestAddReq testAddReq);
    Object updateTest(TestUpdateReq testUpdateReq);
    Object delTest(String id);
    Object listTest(CpsgrpDO cpsgrpDO,PageData pageData);
}
