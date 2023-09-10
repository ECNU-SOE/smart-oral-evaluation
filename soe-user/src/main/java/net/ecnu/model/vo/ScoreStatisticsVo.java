package net.ecnu.model.vo;

import lombok.Data;
import net.ecnu.model.vo.dto.ClassScoreAnalysis;

import java.util.List;

/**
 * @description:课程下的考试、测验成绩分析Vo类
 * @Author lsy
 * @Date 2023/9/10 11:16
 */
@Data
public class ScoreStatisticsVo {

    /**
     * 语料组id
     * **/
    private String cpsgrpId;

    /**
     * 班级的成绩分析
     * **/
    List<ClassScoreAnalysis> classScoreAnalysisList;
}
