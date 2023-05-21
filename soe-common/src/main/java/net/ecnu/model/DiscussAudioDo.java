package net.ecnu.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 17:24
 */
/**
    * 讨论-音频表
    */
@Data
public class DiscussAudioDo implements Serializable {
    /**
    * 话题音频id
    */
    @TableId(type = IdType.AUTO)
    private Long audioId;

    /**
    * 讨论id
    */
    private Long discussId;

    /**
    * 音频url
    */
    private String audioUrl;

    /**
    * 发布时间
    */
    private Date uploadTime;

    /**
    * 逻辑删除标识位
    */
    private Boolean delFlg;

    private static final long serialVersionUID = 1L;
}