package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotBlank;

@Data
public class TestAddReq {

    @NotBlank(message = "语料组ID不能为空",groups = {Create.class})
    private String cpsgrpId;

    @NotBlank(message = "班级ID不能为空",groups = {Create.class})
    private String classId;


    private Integer type;
}
