package net.ecnu.model.dto;

import lombok.Data;

/**
 * @description:错题信息类
 * @Author lsy
 * @Date 2023/10/10 0:38
 */
@Data
public class MistakeInfoDto {

    /**
     * 题组id
     * **/
    private String cpsgrpId;

    /**
     * 快照id
     * **/
    private String cpsrcdId;

}