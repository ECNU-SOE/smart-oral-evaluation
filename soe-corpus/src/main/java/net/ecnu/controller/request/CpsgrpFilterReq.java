package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CpsgrpFilterReq {

    /**
     * 课程id
     */
    private String classId;

    /**
     * 语料组名称
     */
    private String title;

    /**
     * 语料组描述
     */
    private String description;
    /**
     * 难易程度：[A～J][0~9]
     */
    private String difficulty;

    /**
     * 公开类型：0-公开；1-私有
     */
    private Integer isPrivate;

    /**
     * 修改状态：0允许修改、1允许创建者修改、2不允许修改
     */
    private Integer modStatus;

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
