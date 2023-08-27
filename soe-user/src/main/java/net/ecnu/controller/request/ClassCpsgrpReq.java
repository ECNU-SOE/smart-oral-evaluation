package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ClassCpsgrpReq {

    @NotBlank(message = "班级id不能为空",groups = {Create.class})
    private String classId;

    @NotBlank(message = "语料组id不能为空",groups = {Create.class})
    private String cpsgrpId;

    private Integer type;

    private Date startTime;

    private Date endTime;
}
