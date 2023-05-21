package net.ecnu.model.dto;

import lombok.Data;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 18:38
 */
@Data
public class DiscussInfoDto {

    //讨论id
    private Long discussId;

    //发布者
    private String publisher;

    //发布者班级
    private String className;

    //发布者班级id
    private String classId;

    //内容
    private String discussContent;

    //点赞数
    private Integer likeCount;

    //回复数
    private Integer replyCount;
}
