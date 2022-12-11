package net.ecnu.service.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.SysDictMapper;
import net.ecnu.model.authentication.SysDict;
import net.ecnu.service.authentication.SysDictService;
import net.ecnu.utils.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDict> all() {
        return sysDictMapper.selectList(null);
    }

    @Override
    public List<SysDict> query(String groupName, String groupCode) {
        if(StringUtils.isBlank(groupName)|| StringUtils.isBlank(groupCode)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(), BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getMessage());
        }
        LambdaQueryWrapper<SysDict> lambdaQ = Wrappers.lambdaQuery();
        lambdaQ.like(StringUtils.isNotEmpty(groupName), SysDict::getGroupName,groupName)
                .like(StringUtils.isNotEmpty(groupCode), SysDict::getGroupCode,groupCode);
        return sysDictMapper.selectList(lambdaQ);
    }

    @Override
    public void update(SysDict sysDict) {
        if(Objects.isNull(sysDict.getId())){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"更新数据必须指定数据更新条件（主键）");
        }
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if(StringUtils.isBlank(currentAccountNo)){
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION.getCode(),"更新字典信息必须携带有效token");
        }
        sysDict.setUpdateBy(currentAccountNo);
        sysDict.setUpdateTime(LocalDateTime.now());
        sysDictMapper.updateById(sysDict);
    }

    @Override
    public void add(SysDict sysDict) {
        if(Objects.isNull(sysDict)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(), BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getMessage());
        }
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if(StringUtils.isBlank(currentAccountNo)){
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION.getCode(),"新增字典必须携带有效token");
        }
        sysDict.setCreateBy(currentAccountNo);
        sysDict.setCreateTime(LocalDateTime.now());
        sysDictMapper.insert(sysDict);
    }

    @Override
    public void delete(Integer id) {
        if(Objects.isNull(id)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"删除数据必须指定数据删除条件（主键）");
        }
        sysDictMapper.deleteById(id);
    }
}
