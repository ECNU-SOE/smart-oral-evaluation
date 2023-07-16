package net.ecnu.model.vo;

import lombok.Data;

/**
 * @description:错题类型VO类
 * @Author lsy
 * @Date 2023/7/15 16:18
 */
@Data
public class MistakeTypeVO {

    private Integer mistakeNum;

    private String mistakeTypeName;

    private Integer mistakeTypeCode;
}
