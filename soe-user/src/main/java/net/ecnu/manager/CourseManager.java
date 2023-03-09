package net.ecnu.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CourseFilterReq;
import net.ecnu.model.CourseDO;

import java.util.List;

public interface CourseManager {
    List<CourseDO> selectByCourseId(String courseId);
    IPage<CourseDO> pageByFilter(CourseFilterReq courseFilter, Page<CourseDO> courseDOPage);
}
