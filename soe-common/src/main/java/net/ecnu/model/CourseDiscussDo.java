package net.ecnu.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 16:22
 */
/**
    * 课程讨论表
    */
@Data
public class CourseDiscussDo implements Serializable {
    /**
    * 讨论id
    */
    @TableId(type = IdType.AUTO)
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
    * 话题内容
    */
    private String discussContent;

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
    * 逻辑删除标识位
    */
    private Boolean delFlg;

    private static final long serialVersionUID = 1L;
}