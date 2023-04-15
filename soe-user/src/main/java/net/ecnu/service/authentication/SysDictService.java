package net.ecnu.service.authentication;

import net.ecnu.model.authentication.SysDict;

import java.util.List;

public interface SysDictService {

    /**
     * 查询所有
     */
    List<SysDict> all();

    /**
     * 根据参数查询
     * @param groupName 分组名称
     * @param groupCode 分组编码
     */
    List<SysDict> query(String groupName, String groupCode);

    /**
     * 根据id更新数据字典项
     * @param sysDict 更新实体(包含id)
     */
    void update(SysDict sysDict);

    /**
     * 新增数据字典项
     * @param sysDict 新增实体
     */
    void add(SysDict sysDict);

    /**
     * 根据id删除数据字典项
     * @param id  删除项的id
     */
    void delete(Integer id);
}
