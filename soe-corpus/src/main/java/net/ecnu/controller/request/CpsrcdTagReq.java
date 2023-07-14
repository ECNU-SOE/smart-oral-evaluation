package net.ecnu.controller.request;

import io.swagger.models.auth.In;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CpsrcdTagReq {

    @NotNull(message = "标签id不能为空",groups = {Create.class})
    private Integer tagId;

    @NotEmpty(message = "cpsrcdId不能为空",groups = {Create.class})
    private String cpsrcdId;

}
