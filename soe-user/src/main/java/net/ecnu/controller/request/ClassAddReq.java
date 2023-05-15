package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ClassAddReq {

    //所属课程id
    @NotBlank(message = "所属课程id不能为空",groups = {Create.class})
    private String courseId;

    //班级名
    private String name;

    private String description;

    private int level;

    private Date startTime;

    private Date endTime;

    private String picture;

    private String notice;

    private int stuLimit;

    private int joinStatus;

    private int dropStatus;

}
