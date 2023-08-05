package net.ecnu.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description:
 * @Author lsy
 * @Date 2023/8/5 9:31
 */

/**
 * 课程讨论表
 */
@Data
@TableName(value = "class_discuss")
public class ClassDiscussDo implements Serializable {
    /**
     * 讨论id
     */
    private Long discussId;

    /**
     * 父级discusss_id,没有则为0
     */
    private Long parentId;

    /**
     * 班级_id
     */
    private String classId;

    /**
     * 发布人
     */
    private String publisher;

    /**
     * 话题标题
     */
    private String discussTitle;

    /**
     * 话题内容
     */
    private String discussContent;

    /**
     * 转发id(discuss_id),没有则为0
     */
    private Long forwardId;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 1-叶节点，0-非叶节点
     */
    private Boolean isLeaf;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 排序时间（根据该字段排序，若设为置顶，则将其设置为2100年）
     */
    private Date sortTime;

    /**
     * 逻辑删除标识位
     */
    private Boolean delFlg;

    /**
     * 回复数
     */
    private Integer replyNumber;
}