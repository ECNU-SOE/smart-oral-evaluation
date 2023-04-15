package net.ecnu.service.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.SysConfigMapper;
import net.ecnu.model.authentication.SysConfig;
import net.ecnu.service.authentication.SysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Override
    public List<SysConfig> queryConfigs(String configLik) {
        QueryWrapper<SysConfig> query = new QueryWrapper<>();
        query.like(StringUtils.isNotEmpty(configLik),"param_name",configLik)
                .or()
                .like(StringUtils.isNotEmpty(configLik),"param_key",configLik);

        return sysConfigMapper.selectList(query);
    }

    @Override
    public void updateConfig(SysConfig sysconfig) {
        if(sysconfig.getId() == null){
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "修改操作必须带主键");
        }else{
            sysConfigMapper.updateById(sysconfig);
        }
    }

    @Override
    public void addConfig(SysConfig sysconfig) {
        sysConfigMapper.insert(sysconfig);
    }

    @Override
    public void deleteConfig(Integer configId) {
        sysConfigMapper.deleteById(configId);
    }
}
