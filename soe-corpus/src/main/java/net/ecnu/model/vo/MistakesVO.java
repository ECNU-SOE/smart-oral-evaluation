package net.ecnu.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description:错题回顾返回前端VO类
 * @Author lsy
 * @Date 2023/7/15 19:09
 */
@Data
public class MistakesVO implements Serializable {

    /**
     * 主键id
     */
    private String cpsrcdId;

    /**
     * topic_cps.id 关联字段
     * **/
    private Integer topicCpsId;

    /**
     * 所属语料组id
     */
    private String cpsgrpId;

    /**
     * 所属大题id
     */
    private String topicId;

    /**
     * 所属大题名称
     * **/
    private String topicTitle;

    /**
     * 题目名称
     * **/
    private String description;

    /**
     * cpsrcd次序
     */
    @JsonProperty("cNum")
    private Integer cNum;

    /**
     * 评测模式
     */
    private Integer evalMode;

    /**
     * 语料难度
     */
    private String difficulty;

    /**
     * 是否启用拼音
     * **/
    private Integer enablePinYin;

    /**
     * 语料内容拼音
     */
    private String pinyin;

    /**
     * 语料文本内容
     */
    private String refText;

    /**
     * 示范音频播放url
     */
    private String audioUrl;

    /**
     * tag列表
     * **/
    private List<String> tagList;

}
