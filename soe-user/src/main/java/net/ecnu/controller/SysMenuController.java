package net.ecnu.controller;

import net.ecnu.model.authentication.RoleCheckedIds;
import net.ecnu.model.authentication.SysMenu;
import net.ecnu.model.authentication.SysMenuNode;
import net.ecnu.service.authentication.SysMenuService;
import net.ecnu.util.JsonData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限管理系统菜单管理模块
 * TODO
 */
@RestController
@RequestMapping("/system/sysmenu")
public class SysMenuController {

    @Resource
    private SysMenuService sysmenuService;

    //菜单管理：查询
    @PostMapping(value = "/tree")
    public List<SysMenuNode> tree(@RequestParam("menuNameLike") String menuNameLike,
                                  @RequestParam("menuStatus") Boolean menuStatus) {

        return sysmenuService.getMenuTree(menuNameLike, menuStatus);
    }

    //菜单管理：修改
    @PostMapping(value = "/update")
    public JsonData update(@RequestBody SysMenu sysMenu) {
        sysmenuService.updateMenu(sysMenu);
        return JsonData.buildSuccess("更新菜单项成功！");
    }

    //菜单管理：新增
    @PostMapping(value = "/add")
    public JsonData add(@RequestBody SysMenu sysMenu) {
        sysmenuService.addMenu(sysMenu);
        return JsonData.buildSuccess("新增菜单项成功！");
    }

    //菜单管理：删除
    @PostMapping(value = "/delete")
    public JsonData delete(@RequestBody SysMenu sysMenu) {
        sysmenuService.deleteMenu(sysMenu);
        return JsonData.buildSuccess("删除菜单项成功!");
    }

    //角色管理:菜单树展示（勾选项、展开项）
    @PostMapping(value = "/checkedtree")
    public Map<String,Object> checkedtree(@RequestParam Integer roleId) {
        Map<String,Object> ret = new HashMap<>();
        ret.put("tree",sysmenuService.getMenuTree("",null));
        ret.put("expandedKeys",sysmenuService.getExpandedKeys());
        ret.put("checkedKeys",sysmenuService.getCheckedKeys(roleId));
        return ret;
    }

    //角色管理：保存菜单勾选结果
    @PostMapping(value = "/savekeys")
    public JsonData savekeys(@RequestBody RoleCheckedIds roleCheckedIds) {
        sysmenuService.saveCheckedKeys(roleCheckedIds.getRoleId(),roleCheckedIds.getCheckedIds());
        return JsonData.buildSuccess("保存菜单权限成功!");
    }

    //系统左侧菜单栏加载，根据登录用户名加载它可以访问的菜单项
    @PostMapping(value = "/tree/user")
    public List<SysMenuNode> usertree(@RequestParam("username") String username) {
        return sysmenuService.getMenuTreeByUsername(username);
    }


    //菜单管理：更新菜单禁用状态
    @PostMapping(value = "/status/change")
    public JsonData updateStatus(@RequestParam Integer menuId,
                                 @RequestParam Boolean status) {
        sysmenuService.updateStatus(menuId, status);
        return JsonData.buildSuccess("菜单禁用状态更新成功！");
    }


    //菜单管理：更新菜单隐藏状态
    @PostMapping(value = "/hidden/change")
    public JsonData updateHidden(@RequestParam Integer menuId,
                                 @RequestParam Boolean hidden) {
        sysmenuService.updateHidden(menuId, hidden);
        return JsonData.buildSuccess("菜单隐藏状态更新成功,刷新页面即可生效！");
    }
}
