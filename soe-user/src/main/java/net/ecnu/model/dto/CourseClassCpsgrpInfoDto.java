package net.ecnu.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @description:课程+班级+试题信息Dto类
 * @Author lsy
 * @Date 2023/8/27 13:24
 */
@Data
public class CourseClassCpsgrpInfoDto {

    /**
     * 课程名称
     * **/
    public String courseName;

    /**
     * 课程开始时间
     * **/
    public Date courseStartTime;

    /**
     * 课程结束时间
     * **/
    public Date courseEndTime;

    /**
     * 班级名称
     * **/
    public String className;

    /**
     * 授课老师名称
     * **/
    public String teacherName;

    /**
     * 学生人数
     * **/
    public Integer studentNums;

    /**
     * 试题组名称
     * **/
    public String cpsgrpName;
}
