package net.ecnu.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description 
 * @author Joshua
 * @date 2023/11/25 12:46
 */

/**
 * 语音评测错题表
 */
@Data
@Accessors(chain = true)
public class MistakeAudioDO implements Serializable {
    /**
     * 错题id
     */
    private Long mistakeId;

    /**
     * 题型-题目关联code(topic_cps.id)
     */
    private Integer topicCpsId;

    /**
     * 快照id（cpsrcd.id）
     */
    private String cpsrcdId;

    /**
     * 错题类型，1-朗读字词,2-朗读句子,3-朗读诗词,4-朗读文章，5-单选题，6-多选题，7-写汉字，8-看视频答题，9-其他类型，没有类型默认为9
     */
    private Integer mistakeType;

    /**
     * 用户id（user.account_no）
     */
    private String userId;

    /**
     * 重复错误次数，默认为1
     */
    private Integer errorSum;

    /**
     * 逻辑删除位，0-未删除，1-已删除
     */
    private Boolean delFlg;

    /**
     * 错题创建时间
     */
    private Date createTime;

    /**
     * 错题更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}