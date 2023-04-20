package net.ecnu.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.ClassFilterReq;
import net.ecnu.model.ClassDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface ClassManager {
    List<ClassDO> pageByFilter(ClassFilterReq classFilter, PageData pageData);

    int countByFilter(ClassFilterReq classFilterReq);


    IPage<ClassDO> pageByFilterLYW(ClassFilterReq classFilterReq, Page<ClassDO> page);
}
