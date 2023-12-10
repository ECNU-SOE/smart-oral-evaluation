package net.ecnu.model.dto.req;

import lombok.Data;

/**
 * @author Joshua
 * @description 后台管理系统 - 查询答题报告
 * @date 2023/12/10 13:29
 */
@Data
public class TranscriptInfoReq {

    /**
     * 语料组名称-支持模糊查询
     * **/
    private String cpsgrpName;

    /**
     * 答题人 - 支持模糊查询
     * **/
    private String respondentName;

    /**
     * 批阅状态 0-未批阅，1-已批阅
     * **/
    private Integer reviewStatus;

    /**
     * 每页数据量
     * **/
    private Integer pageSize;

    /**
     * 页数
     * **/
    private Integer pageNumber;

}
