package net.ecnu.model.authentication;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sys_role
 * @author 字母哥
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseColumns {
    private Integer id;

    /**
     * 角色名称(汉字)
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 角色的英文code.如：ADMIN
     */
    private String roleCode;

    /**
     * 角色顺序
     */
    private Integer roleSort;

    /**
     * 0表示可用
     */
    private Boolean status;


}