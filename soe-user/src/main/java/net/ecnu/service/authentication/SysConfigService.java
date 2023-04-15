package net.ecnu.service.authentication;

import net.ecnu.model.authentication.SysConfig;

import java.util.List;

public interface SysConfigService {

    List<SysConfig> queryConfigs(String configLik);

    void updateConfig(SysConfig sysconfig);

    void addConfig(SysConfig sysconfig);

    void deleteConfig(Integer configId);
}
