package net.ecnu.model.dto;

import lombok.Data;

/**
 * @description:课程下的考试测验成绩分析
 * @Author lsy
 * @Date 2023/9/10 11:03
 */
@Data
public class ScoreStatisticsReq {

    /**
     * 语料组id
     */
    private String cpsgrpId;

    /**
     * 课程id
     * **/
    private String courseId;


}
