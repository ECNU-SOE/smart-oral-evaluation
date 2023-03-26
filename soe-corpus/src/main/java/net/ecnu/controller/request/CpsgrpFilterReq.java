package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CpsgrpFilterReq {

    /**
     * 语料组名称
     */
    private String title;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 语料组类型 1：考试、2：作业、3：测验
     */
    private Integer type;

    /**
     * 语料组描述
     */
    private String description;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

}
