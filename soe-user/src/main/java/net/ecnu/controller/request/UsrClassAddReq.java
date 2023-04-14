package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotBlank;

@Data
public class UsrClassAddReq {

    private String accountNo;

    @NotBlank(message = "班级号不能为空",groups = {Create.class})
    private String classId;

    private Integer type;
}
