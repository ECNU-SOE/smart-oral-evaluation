package net.ecnu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.model.authentication.SysDict;
import net.ecnu.service.authentication.SysDictService;
import net.ecnu.util.JsonData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 权限管理系统-字典管理模块
 */
@Api(value = "字典管理模块")
@RestController
@RequestMapping("/system/sysdict")
public class SysDictController {

    @Resource
    private SysDictService sysDictService;

    /**
     * 查询所有
     * @return List<SysDict> 所有数据字典项
     */
    @ApiOperation("查询所有数据字典项")
    @PostMapping(value = "/all")
    public List<SysDict> all() {
        return sysDictService.all();
    }

    /**
     * 根据查询参数参训数据字典
     * @param groupName 分组名称
     * @param groupCode 分组编码
     * @return 数据字典项列表
     */
    @ApiOperation("根据查询参数参训数据字典")
    @PostMapping(value = "/query")
    public List<SysDict> query(@RequestParam("groupName") String groupName, @RequestParam("groupCode") String groupCode  ) {
        return sysDictService.query(groupName, groupCode );
    }

    /**
     * 根据id更新数据数据字典项目
     * @param sysDict 更新实体（必须包含id）
     * @return 更新成功结果
     */
    @ApiOperation("根据id更新数据数据字典项目")
    @PostMapping(value = "/update")
    public JsonData update(@RequestBody SysDict sysDict) {
        if(Objects.isNull(sysDict)){
            return JsonData.buildCodeAndMsg(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(), BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getMessage());
        }
        sysDictService.update(sysDict);
        return JsonData.buildSuccess("更新数据字典项成功！");
    }

    /**
     * 新增数据字典项
     * @param sysDict 新增实体
     * @return 更新成功结果
     */
    @ApiOperation("新增数据字典项")
    @PostMapping(value = "/add")
    public JsonData add(@RequestBody SysDict sysDict) {
        sysDictService.add(sysDict);
        return JsonData.buildSuccess("新增数据字典项成功！");
    }

    /**
     * 根据id删除数据字典项
     * @param id 删除项id
     * @return 删除成功结果
     */
    @ApiOperation("根据id删除数据字典项")
    @PostMapping(value = "/delete")
    public JsonData delete(@RequestParam Integer id) {
        sysDictService.delete(id);
        return JsonData.buildSuccess("删除数据字典项成功!");
    }

}
