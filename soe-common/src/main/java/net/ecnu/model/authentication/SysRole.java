package net.ecnu.model.authentication;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sys_role
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseColumns {

    @TableId(value ="id",type = IdType.AUTO)
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