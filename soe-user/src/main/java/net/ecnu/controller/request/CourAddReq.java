package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CourAddReq {

    /**
     * 课程名
     */
    @NotBlank(message = "课程名字不能为空",groups = {Create.class})
    private String name;

    /**
     * 课程描述
     */
    private String description;


    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

}
