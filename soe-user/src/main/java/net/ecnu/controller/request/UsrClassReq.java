package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsrClassReq {

    private String accountNo;

    @NotBlank(message = "班级号不能为空",groups = {Create.class,Update.class})
    private String classId;

    @NotNull(message = "关系类型不能为空",groups = {Update.class})
    @JsonProperty("rType")
    private Integer rType;

}
