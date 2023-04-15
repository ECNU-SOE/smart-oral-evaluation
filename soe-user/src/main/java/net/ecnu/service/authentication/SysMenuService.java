package net.ecnu.service.authentication;

import net.ecnu.model.authentication.SysMenu;
import net.ecnu.model.authentication.SysMenuNode;

import java.util.List;

public interface SysMenuService {

    /**
     * 菜单管理：根据查询条件查询树形结构菜单列表
     * @param menuNameLike 菜单名称
     * @param menuStatus 菜单可用状态
     * @return 菜单列表或树形列表
     */
    List<SysMenuNode> getMenuTree(String menuNameLike, Boolean menuStatus);

    void updateMenu(SysMenu sysmenu);

    void addMenu(SysMenu sysmenu);

    void deleteMenu(SysMenu sysMenu);

    List<String> getCheckedKeys(Integer roleId);

    List<String> getExpandedKeys();

    void saveCheckedKeys(Integer roleId, List<Integer> checkedIds);

    List<SysMenuNode> getMenuTreeByUsername(String accountNo);

    void updateStatus(Integer id, Boolean status);

    void updateHidden(Integer id, Boolean hidden);
}
