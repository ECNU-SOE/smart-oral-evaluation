package net.ecnu.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description:讨论内容前端展示实体类
 * @Author lsy
 * @Date 2023/5/28 11:13
 */
@Data
public class DiscussVo {

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
     * 发布人Code
     */
    private String publisher;

    /**
     * 发布人姓名 + 班级角色
     * **/
    private String publisherName;

    /**
     * 话题标题
     * **/
    private String discussTitle;

    /**
     * 转发班级数
     * **/
    private Integer publishClassesNum;

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
     * 该用户是否对该帖点过赞
     * **/
    private Boolean isLike;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 回复数
     * */
    private Integer replyNumber;

    /**
     * 话题音频url集合
     * */
    private List<String> audioList;

    /**
     * 转发话题内容
     * */
    private DiscussVo forwardDiscuss;
}
