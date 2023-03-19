package net.ecnu.manager;

import net.ecnu.controller.request.CourFilterReq;
import net.ecnu.model.CourseDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface CourseManager {
    List<CourseDO> pageByFilter(CourFilterReq courseFilter, PageData pageData);

    int countByFilter(CourFilterReq courFilterReq);
}
