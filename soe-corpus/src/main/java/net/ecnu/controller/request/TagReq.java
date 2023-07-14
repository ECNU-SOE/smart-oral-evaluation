package net.ecnu.controller.request;

import io.swagger.models.auth.In;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TagReq {

    @NotNull(message = "id can't be null in update",groups = {Update.class})
    private Integer id;

    @NotEmpty(message = "name can't be null in create",groups = {Create.class})
    private String name;

    private Integer time;

    private Double weight;

    private Integer category;


}
