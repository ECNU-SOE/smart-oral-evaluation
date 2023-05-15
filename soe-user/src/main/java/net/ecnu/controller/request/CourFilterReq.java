package net.ecnu.controller.request;

import lombok.Data;

import java.lang.ref.PhantomReference;
import java.util.Date;

@Data
public class CourFilterReq {
    /**
     * 班级id
     */
    private String id;

    /**
     * 课程名
     */
    private String name;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程创建者
     */
    private String creator;

    private Integer difficulty;


}
