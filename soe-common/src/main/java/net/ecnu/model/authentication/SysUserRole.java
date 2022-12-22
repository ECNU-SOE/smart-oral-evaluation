package net.ecnu.model.authentication;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * sys_user_role
 */
@Data
@TableName("user_role")
public class SysUserRole {

    /**
     * 角色自增id
     */
    private Long roleId;

    /**
     * 用户自增id
     */
    private Long userId;


}