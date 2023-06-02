package net.ecnu.controller.request;

import io.swagger.models.auth.In;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Find;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class SignReq {

    @NotBlank(message = "月份不能为空",groups = {Find.class})
    private Integer month;
}
