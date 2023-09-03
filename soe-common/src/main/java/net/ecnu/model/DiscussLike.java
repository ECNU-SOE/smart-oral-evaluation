package net.ecnu.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @Author lsy
 * @Date 2023/9/3 13:36
 */
/**
    * 讨论点赞用户表
    */
@Data
@NoArgsConstructor
public class DiscussLike implements Serializable {
    /**
    * 主键id
    */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
    * 讨论id
    */
    private Long discussId;

    /**
    * 用户唯一id（user.account_no）
    */
    private String userId;

    /**
    * 点赞时间
    */
    private Date createTime;

    /**
    * 逻辑删除标识位
    */
    private Boolean delFlg;

    private static final long serialVersionUID = 1L;
}