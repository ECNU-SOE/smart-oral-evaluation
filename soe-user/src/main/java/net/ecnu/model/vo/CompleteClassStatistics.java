package net.ecnu.model.vo;

import lombok.Data;

/**
 * @description:班级完成度
 * @Author lsy
 * @Date 2023/9/10 21:10
 */
@Data
public class CompleteClassStatistics {

    /**
     * 语料组id
     * **/
    private String cpsgrpId;

    /**
     * 语料组名称
     * **/
    private String cpsgrpName;

    /**
     * 1：测评，2：考试
     * **/
    private Integer cpsgrpType;

    /**
     * 班级测评/考试完成度
     */
    private String completeRate;
}
