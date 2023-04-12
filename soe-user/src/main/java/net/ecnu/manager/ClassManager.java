package net.ecnu.manager;

import net.ecnu.controller.request.ClassFilterReq;
import net.ecnu.model.ClassDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface ClassManager {
    List<ClassDO> pageByFilter(ClassFilterReq classFilter, PageData pageData);

    int countByFilter(ClassFilterReq classFilterReq);


}
