package net.ecnu.model.dto;

import lombok.Data;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/28 10:44
 */
@Data
public class ForwardDto {

    //转发评论
    private String discussTest;

    //班级id
    private String classId;

    //转发id（discussId）
    private Long forwardId;
}
