package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotBlank;

@Data
public class UsrCourAddReq {

    @NotBlank(message = "用户账号不能为空",groups = {Create.class})
    private String accountNo;

    /**
     * 课程id
     */
    @NotBlank(message = "课程号不能为空",groups = {Create.class})
    private String courseId;
}
