package net.ecnu.model.vo.dto;

import lombok.Data;

/**
 * @description:班级下拉框数据类
 * @Author lsy
 * @Date 2023/9/10 13:05
 */
@Data
public class ClassOptions {

    /**
     * 班级ID
     * **/
    private String classId;

    /**
     * 班级名称
     * **/
    private String className;

}
