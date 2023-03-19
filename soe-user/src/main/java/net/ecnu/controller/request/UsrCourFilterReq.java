package net.ecnu.controller.request;

import lombok.Data;

import java.util.Date;

@Data
public class UsrCourFilterReq {

    private Long id;

    private String accountNo;

    private String courseId;

    private Integer rType;

    private Date gmtCreate;

    private Date gmtModified;
}
