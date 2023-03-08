package net.ecnu.manager;

import net.ecnu.model.CourseDO;

import java.util.List;

public interface CourseManager {
    List<CourseDO> selectAllByCourseId(String courseId);
}
