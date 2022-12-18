package net.ecnu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.ecnu.model.authentication.SysRole;
import net.ecnu.model.vo.dto.UserRoleCheckedIds;
import net.ecnu.service.authentication.SysRoleService;
import net.ecnu.util.JsonData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 权限管理系统-角色管理模块
 */
@Api(value = "角色管理模块")
@RestController
@RequestMapping("/system/sysrole")
public class SysRoleController {

    @Resource
    private SysRoleService sysroleService;

    //角色管理:查询
    @ApiImplicitParam(name = "roleLike",value = "角色模糊字段",required = false)
    @ApiOperation("角色管理:查询")
    @PostMapping(value = "/query")
    public JsonData query(@RequestParam("roleLike") String roleLike) {
        List<SysRole> sysRoles = sysroleService.queryRoles(roleLike);
        return JsonData.buildSuccess(sysRoles);
    }

    //角色管理：修改
    @ApiOperation("角色管理：修改")
    @PostMapping(value = "/update")
    public JsonData update(@RequestBody SysRole sysRole) {
        sysroleService.updateRole(sysRole);
        return JsonData.buildSuccess("更新角色成功！");
    }

    //角色管理：新增
    @ApiOperation("角色管理：新增")
    @PostMapping(value = "/add")
    public JsonData add(@RequestBody SysRole sysRole) {
        sysroleService.addRole(sysRole);
        return JsonData.buildSuccess("新增角色成功！");
    }

    //角色管理：删除
    @ApiOperation("角色管理：删除")
    @PostMapping(value = "/delete")
    public JsonData delete(@RequestParam Integer roleId) {
        sysroleService.deleteRole(roleId);
        return JsonData.buildSuccess("删除角色成功!");
    }

    //用户管理：为用户分配角色，展示角色列表及勾选角色列表
    @ApiOperation("为用户分配角色，展示角色列表及勾选角色列表")
    @PostMapping(value = "/checkedroles")
    public JsonData checkedroles(@RequestParam Integer userId) {
        Map<String, Object> rolesAndChecked = sysroleService.getRolesAndChecked(userId);
        return JsonData.buildSuccess(rolesAndChecked);
    }

    //用户管理：保存用户角色
    @ApiOperation("保存用户角色")
    @PostMapping(value = "/savekeys")
    public JsonData savekeys(@RequestBody UserRoleCheckedIds userRoleCheckedIds) {
        sysroleService.saveCheckedKeys(
                userRoleCheckedIds.getUsername(),
                userRoleCheckedIds.getUserId(),
                userRoleCheckedIds.getCheckedIds()
        );
        return JsonData.buildSuccess("保存用户角色成功!");
    }


    //角色管理：更新角色禁用状态*
    @ApiOperation("更新角色禁用状态")
    @PostMapping(value = "/status/change")
    public JsonData update(@RequestParam Integer roleId,
                           @RequestParam Boolean status) {
        sysroleService.updateStatus(roleId, status);
        return JsonData.buildSuccess("角色禁用状态更新成功！");
    }
}
