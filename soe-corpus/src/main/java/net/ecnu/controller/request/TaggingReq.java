package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Delete;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TaggingReq {

    @NotNull(message = "标签id不能为空",groups = {Create.class})
    private Integer tagId;

    @NotEmpty(message = "标签名称不能为空",groups = {Delete.class})
    private String tagName;

    @NotEmpty(message = "cpsrcdId不能为空",groups = {Create.class,Delete.class})
    private String entityId;

    private Integer entityType;

}
