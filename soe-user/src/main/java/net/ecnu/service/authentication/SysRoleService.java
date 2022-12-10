package net.ecnu.service.authentication;

import net.ecnu.model.authentication.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService {

    List<SysRole> queryRoles(String roleLik);

    void updateRole(SysRole sysrole);

    void deleteRole(Integer id);

    void updateStatus(Integer id, Boolean status);

    void addRole(SysRole sysrole);

    Map<String,Object> getRolesAndChecked(Integer userId);

    void saveCheckedKeys(String phone, Integer userId, List<Integer> checkedIds);
}
