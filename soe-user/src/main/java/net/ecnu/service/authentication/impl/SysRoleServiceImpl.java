package net.ecnu.service.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.SysRoleMapper;
import net.ecnu.mapper.SysUserRoleMapper;
import net.ecnu.mapper.SystemMapper;
import net.ecnu.model.authentication.SysRole;
import net.ecnu.model.authentication.SysUserRole;
import net.ecnu.service.authentication.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SystemMapper systemMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRole> queryRoles(String roleLik) {
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        query.like(StringUtils.isNotEmpty(roleLik),"role_code",roleLik)
                .or()
                .like(StringUtils.isNotEmpty(roleLik),"role_desc",roleLik)
                .or()
                .like(StringUtils.isNotEmpty(roleLik),"role_name",roleLik);
        query.orderByAsc("role_sort");

        return sysRoleMapper.selectList(query);
    }

    @Override
    public void updateRole(SysRole sysrole) {
        if(Objects.isNull(sysrole.getId())){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"更新数据必须指定数据更新条件（主键）");
        }
        sysRoleMapper.updateById(sysrole);
    }

    @Override
    public void deleteRole(Integer id) {
        if(Objects.isNull(id)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"删除数据必须指定数据删除条件（主键）");
        }
        sysRoleMapper.deleteById(id);
    }

    @Override
    public void updateStatus(Integer id, Boolean status) {
        if(Objects.isNull(id)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"修改操作必须带主键");
        }
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRole.setStatus(status);
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public void addRole(SysRole sysrole) {
        sysrole.setStatus(false); //是否禁用:false
        sysRoleMapper.insert(sysrole);
    }

    @Override
    public Map<String, Object> getRolesAndChecked(Integer userId) {
        if(Objects.isNull(userId)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"获取角色信息必须传入用户id作为参数");
        }
        Map<String,Object> ret = new HashMap<>();
        //所有角色记录
        ret.put("roleDatas",sysRoleMapper.selectList(null));
        //某用户具有的角色id列表
        ret.put("checkedRoleIds",systemMapper.getCheckedRoleIds(userId));
        return ret;
    }

    @Override
    public void saveCheckedKeys(String phone, Integer userId, List<Integer> checkedIds) {
        sysUserRoleMapper.delete(new UpdateWrapper<SysUserRole>()
                .eq("user_id",userId));
        systemMapper.insertUserRoleIds(userId,checkedIds);
    }
}
