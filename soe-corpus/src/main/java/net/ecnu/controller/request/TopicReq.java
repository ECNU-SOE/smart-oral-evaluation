package net.ecnu.controller.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TopicReq {

    /**
     * 题目类型id
     */
    @NotEmpty(message = "id can't be empty in update", groups = {Update.class})
    private String id;

    /**
     * 所属题目组id
     */
    @NotEmpty(message = "cpsgrpId can't be empty in create", groups = {Create.class})
    private String cpsgrpId;

    /**
     * topic次序
     */
    @JsonProperty("tNum")
    @NotNull(message = "tNum can't be null in create", groups = {Create.class})
    private Integer tNum;

    /**
     * 题目名称
     */
    private String title;

    /**
     * 所占分值
     */
    private Double score;

    /**
     * 大题难度
     */
    private Integer difficulty;

    /**
     * 题目备注说明
     */
    private String description;

}
