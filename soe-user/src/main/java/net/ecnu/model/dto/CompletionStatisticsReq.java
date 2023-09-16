package net.ecnu.model.dto;

import lombok.Data;

/**
 * @description:课程下班级测评/考试完成度统计
 * @Author lsy
 * @Date 2023/9/10 20:50
 */
@Data
public class CompletionStatisticsReq {

    /**
     * 课程id
     * **/
    private String courseId;

    /**
     * 班级id
     * **/
    private String classId;
}
