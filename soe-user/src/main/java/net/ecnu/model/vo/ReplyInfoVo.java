package net.ecnu.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description:回复信息前端展示类
 * @Author lsy
 * @Date 2023/5/28 14:20
 */
@Data
public class ReplyInfoVo {

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
     * 该用户是否对该帖点过赞
     * **/
    private Boolean isLike;

    /**
     * 1-叶节点，0-非叶节点
     */
    private Boolean isLeaf;

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
}
