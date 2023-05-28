package net.ecnu.controller.request;

import io.swagger.models.auth.In;
import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class SignReq {

    @NotBlank(message = "签到日期不能为空",groups = {Create.class})
    private Date signTime;

    private Integer signType;

}
