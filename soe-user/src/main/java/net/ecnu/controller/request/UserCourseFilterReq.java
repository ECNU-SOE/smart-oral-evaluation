package net.ecnu.controller.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class UserCourseFilterReq {

    private Long id;

    private String accountNo;

    private String courseId;

    private Integer rType;

    private Date gmtCreate;

    private Date gmtModified;
}
