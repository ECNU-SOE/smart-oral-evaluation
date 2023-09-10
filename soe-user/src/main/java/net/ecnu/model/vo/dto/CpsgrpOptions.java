package net.ecnu.model.vo.dto;

import lombok.Data;

/**
 * @description:语料组下拉框数据类
 * @Author lsy
 * @Date 2023/9/10 13:16
 */
@Data
public class CpsgrpOptions {

    /**
     * 语料组id
     * **/
    private String cpsgrpId;

    /**
     * 语料组名称
     * **/
    private String cpsgrpName;
}
