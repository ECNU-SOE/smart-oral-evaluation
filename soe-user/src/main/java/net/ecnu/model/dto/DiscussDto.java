package net.ecnu.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description:话题讨论传输实体类
 * @Author lsy
 * @Date 2023/5/21 16:49
 */
@Data
@Accessors(chain = true)
public class DiscussDto {

    private String discussTitle;

    private String discussTest;

    private List<String> audioUrl;

    private String classId;

    private Long parentId;
}
