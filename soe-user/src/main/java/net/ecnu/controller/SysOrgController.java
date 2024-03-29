package net.ecnu.controller;

import io.swagger.annotations.Api;
import net.ecnu.model.UserDO;
import net.ecnu.model.authentication.SysOrg;
import net.ecnu.model.authentication.SysOrgNode;
import net.ecnu.service.authentication.SysOrgService;
import net.ecnu.service.authentication.SysUserService;
import net.ecnu.util.JsonData;
import net.ecnu.util.RequestParamUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 培训管理系统-组织管理模块
 */
@Api(value = "组织管理模块")
@RestController
@RequestMapping("/system/sysorg")
public class SysOrgController {

    @Resource
    private SysOrgService sysorgService;
    @Resource
    private SysUserService sysuserService;

    @PostMapping(value = "/tree")
    public JsonData tree(@RequestParam(value = "orgNameLike",required = false) String orgNameLike,
                                 @RequestParam(value = "orgStatus",required = false) Boolean orgStatus) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        UserDO sysUser = sysuserService.getUserByUserName(currentAccountNo);
        List<SysOrgNode> orgTreeById = sysorgService.getOrgTreeById(sysUser.getOrgId(), orgNameLike, orgStatus);
        return JsonData.buildSuccess(orgTreeById);
    }

    @PostMapping(value = "/update")
    public JsonData update(@RequestBody SysOrg sysOrg) {
        sysorgService.updateOrg(sysOrg);
        return JsonData.buildSuccess("更新组织机构成功！");
    }

    @PostMapping(value = "/add")
    public JsonData add(@RequestBody SysOrg sysOrg) {
        sysorgService.addOrg(sysOrg);
        return JsonData.buildSuccess("新增组织机构成功！");
    }


    @PostMapping(value = "/delete")
    public JsonData delete(@RequestBody SysOrg sysOrg) {
        sysorgService.deleteOrg(sysOrg);
        return JsonData.buildSuccess("删除组织机构成功!");
    }

    //组织管理：更新组织禁用状态
    @PostMapping(value = "/status/change")
    public JsonData update(@RequestParam Integer orgId,
                           @RequestParam Boolean status) {
        sysorgService.updateStatus(orgId, status);
        return JsonData.buildSuccess("组织禁用状态更新成功！");
    }
}
