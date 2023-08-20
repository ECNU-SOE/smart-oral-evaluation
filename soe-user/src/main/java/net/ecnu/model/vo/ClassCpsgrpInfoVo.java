package net.ecnu.model.vo;

import lombok.Data;

/**
 * @description: 课程下布置的试题信息
 * @Author lsy
 * @Date 2023/8/20 12:22
 */
@Data
public class ClassCpsgrpInfoVo {

    /**
     * 试题组id
     * **/
    private String cpsgrpId;

    /**
     * 试题组名称
     * **/
    private String cpsgrpName;
}
