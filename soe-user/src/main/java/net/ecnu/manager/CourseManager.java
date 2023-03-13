package net.ecnu.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.model.CourseDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface CourseManager {
    List<CourseDO> pageByFilter(CourseFilterReq courseFilter, PageData pageData);

    int countByFilter(CourseFilterReq courseFilterReq);
}
