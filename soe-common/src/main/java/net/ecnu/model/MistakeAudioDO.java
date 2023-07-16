package net.ecnu.model;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @Author lsy
 * @Date 2023/7/15 15:50
 */
/**
    * 语音评测错题表
    */
@Data
@NoArgsConstructor
public class MistakeAudioDO {
    /**
    * 错题id
    */
    private Long mistakeId;

    /**
    * 快照id（cpsrcd.id）
    */
    private String cpsrcdId;

    /**
    * 错题类型，默认为0-语音评测
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
}