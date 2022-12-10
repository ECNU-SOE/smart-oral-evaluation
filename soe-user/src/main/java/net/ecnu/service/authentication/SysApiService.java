package net.ecnu.service.authentication;

import net.ecnu.model.authentication.SysApi;
import net.ecnu.model.authentication.SysApiNode;

import java.util.List;

public interface SysApiService {

    List<SysApiNode> getApiTree(String apiNameLike, Boolean apiStatus);

    void addApi(SysApi sysapi);

    void updateApi(SysApi sysapi);

    void deleteApi(SysApi sysApi);

    List<String> getCheckedKeys(Integer roleId);

    List<String> getExpandedKeys();

    void saveCheckedKeys(String roleCode, Integer roleId, List<Integer> checkedIds);

    void updateStatus(Long id, Boolean status);
}
