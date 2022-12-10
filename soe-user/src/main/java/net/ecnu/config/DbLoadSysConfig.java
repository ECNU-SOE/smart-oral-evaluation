package net.ecnu.config;

import net.ecnu.mapper.SysConfigMapper;
import net.ecnu.model.authentication.SysConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 管理系统内全局参数，比如：默认的用户密码，皮肤等配置
 * 核心数据库表为sys_config.
 * 应用启动时一次性加载全部配置到内存，程序内调用getConfigItem(key)获取参数
 */
@Component
public class DbLoadSysConfig implements CommandLineRunner {

    @Resource
    private SysConfigMapper sysConfigMapper;

    private List<SysConfig> sysConfigList;

    //根据参数key，获取参数值
    public  String getConfigItem(String paramKey){
        Optional<SysConfig> temp =  sysConfigList.stream()
                .filter(str -> str.getParamKey().equals(paramKey))
                .findFirst();

        return temp.orElse(new SysConfig()).getParamValue();
    }

    //应用启动加载参数配置
    @Override
    public void run(String... args) throws Exception {
        sysConfigList = sysConfigMapper.selectList(null);
    }

    //获取所有参数配置项
    public  List<SysConfig> getSysConfigList() {
        sysConfigList = sysConfigMapper.selectList(null);
        return sysConfigList;
    }
}
