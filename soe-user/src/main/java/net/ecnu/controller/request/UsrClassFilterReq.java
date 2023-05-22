package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

@Data
public class UsrClassFilterReq {
    private String classId;
    @JsonProperty("rType")
    private Integer rType;

//    private Integer status;
}
