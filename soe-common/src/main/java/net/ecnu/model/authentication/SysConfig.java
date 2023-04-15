package net.ecnu.model.authentication;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sys_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseColumns {
    private Long id;

    /**
     * 参数名称(中文)
     */
    private String paramName;

    /**
     * 参数唯一标识(英文及数字)
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 参数描述备注
     */
    private String paramDesc;



}