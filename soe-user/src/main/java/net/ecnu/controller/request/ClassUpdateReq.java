package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotBlank;

@Data
public class ClassUpdateReq {

    @NotBlank(message = "必须指定班级id",groups = {Update.class})
    private String id;
    //班级名
    private String name;

    private String description;

    private int level;

}
