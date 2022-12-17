package net.ecnu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ecnu.config.DbLoadSysConfig;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.model.authentication.SysConfig;
import net.ecnu.service.authentication.SysConfigService;
import net.ecnu.util.JsonData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 权限管理系统-配置管理模块
 */
@Api(value = "配置管理模块")
@RestController
@RequestMapping("/system/sysconfig")
public class SysConfigController {

    @Resource
    private DbLoadSysConfig dbLoadSysConfig;

    @Resource
    private SysConfigService sysconfigService;

    @PostMapping(value = "/all")
    public List<SysConfig> all() {
        return dbLoadSysConfig.getSysConfigList();
    }

    @PostMapping(value = "/refresh")
    public List<SysConfig> refresh() {
        return dbLoadSysConfig.getSysConfigList();
    }

    @PostMapping(value = "/query")
    public List<SysConfig> query(@RequestParam("configLike") String configLike) {
        return sysconfigService.queryConfigs(configLike);
    }

    @PostMapping(value = "/update")
    public JsonData update(@RequestBody SysConfig sysConfig) {
        sysconfigService.updateConfig(sysConfig);
        return JsonData.buildSuccess("更新配置成功！");
    }

    @PostMapping(value = "/add")
    public JsonData add(@RequestBody SysConfig sysConfig) {
        if(Objects.isNull(sysConfig)){
            return JsonData.buildCodeAndMsg(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "新增操作必须带上新配置信息");
        }
        sysconfigService.addConfig(sysConfig);
        return JsonData.buildSuccess("新增配置成功！");
    }

    @PostMapping(value = "/delete")
    public JsonData delete(@RequestParam Integer configId) {
        if(Objects.isNull(configId)){
            return JsonData.buildCodeAndMsg(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "configId不能为空");
        }
        sysconfigService.deleteConfig(configId);
        return JsonData.buildSuccess("删除配置成功!");
    }

}
