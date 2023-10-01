package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CpsrcdReq {

    /**
     * 主键id
     */
    @NotEmpty(message = "id can't be empty in update", groups = {Update.class})
    private String id;

    /**
     * 语料类型
     */
    @NotEmpty(message = "语料类型不能为空", groups = {Create.class})
    private String type;

    /**
     * 评测模式
     */
    private Integer evalMode;

    /**
     * 语料难度
     */
    private Integer difficulty;

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
     * 标签列表
     */
    private List<Integer> tagIds;

}
