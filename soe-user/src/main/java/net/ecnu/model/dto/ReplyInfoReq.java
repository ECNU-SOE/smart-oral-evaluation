package net.ecnu.model.dto;

import lombok.Data;

/**
 * @description:查询班级下讨论的内容，不包含回复，支持排序请求类
 * @Author lsy
 * @Date 2023/9/3 10:52
 */
@Data
public class ReplyInfoReq {

    /**
     * 班级id
     * **/
    private String classId;

    /**
     * 当前页
     * **/
    private Integer pageNum;

    /**
     * 每页数据量
     * **/
    private Integer pageSize;

    /**
     * 按时间排序
     * **/
    private Integer sortByTime;

    /**
     * 按点赞数排序
     * **/
    private Integer sortByLikes;
}
