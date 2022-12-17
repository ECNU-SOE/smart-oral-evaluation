package net.ecnu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ecnu.model.authentication.RoleCheckedIds;
import net.ecnu.model.authentication.SysApi;
import net.ecnu.model.authentication.SysApiNode;
import net.ecnu.service.authentication.SysApiService;
import net.ecnu.util.JsonData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限管理系统-接口管理模块
 */
@Api(value = "接口管理模块")
@RestController
@RequestMapping("/system/sysapi")
public class SysApiController {

    @Resource
    private SysApiService sysapiService;

    //接口管理:查询
    @ApiOperation("接口管理:查询")
    @PostMapping(value = "/tree")
    public List<SysApiNode> tree(@RequestParam("apiNameLike") String apiNameLike,
                                 @RequestParam("apiStatus") Boolean apiStatus) {

        return sysapiService.getApiTree(apiNameLike, apiStatus);
    }

    //接口管理:修改
    @ApiOperation("接口管理:修改")
    @PostMapping(value = "/update")
    public JsonData update(@RequestBody SysApi sysApi) {
        sysapiService.updateApi(sysApi);
        return JsonData.buildSuccess("更新接口配置成功！");
    }

    //接口管理:新增
    @ApiOperation("接口管理:新增")
    @PostMapping(value = "/add")
    public JsonData add(@RequestBody SysApi sysApi) {
        sysapiService.addApi(sysApi);
        return JsonData.buildSuccess("新增接口配置成功！");
    }

    //接口管理:删除
    @ApiOperation("接口管理:删除")
    @PostMapping(value = "/delete")
    public JsonData delete(@RequestBody SysApi sysApi) {
        sysapiService.deleteApi(sysApi);
        return JsonData.buildSuccess("删除接口配置成功!");
    }

    //角色管理：API树展示（勾选项、展开项）
    @ApiOperation("角色管理：API树展示（勾选项、展开项）")
    @PostMapping(value = "/checkedtree")
    public Map<String,Object> checkedtree(@RequestParam Integer roleId) {
        Map<String,Object> ret = new HashMap<>();
        ret.put("tree",sysapiService.getApiTree("",null));
        ret.put("expandedKeys",sysapiService.getExpandedKeys());
        ret.put("checkedKeys",sysapiService.getCheckedKeys(roleId));
        return ret;
    }

    //角色管理：保存API权限勾选结果
    @ApiOperation("角色管理：保存API权限勾选结果")
    @PostMapping(value = "/savekeys")
    public JsonData savekeys(@RequestBody RoleCheckedIds roleCheckedIds) {
        sysapiService.saveCheckedKeys(
                roleCheckedIds.getRoleCode(),
                roleCheckedIds.getRoleId(),
                roleCheckedIds.getCheckedIds()
        );
        return JsonData.buildSuccess("保存接口权限成功!");
    }

    //接口管理：更新接口禁用状态
    @ApiOperation("接口管理：更新接口禁用状态")
    @PostMapping(value = "/status/change")
    public JsonData update(@RequestParam Long apiId,
                           @RequestParam Boolean status) {
        sysapiService.updateStatus(apiId, status);
        return JsonData.buildSuccess("接口禁用状态更新成功！");
    }
}

