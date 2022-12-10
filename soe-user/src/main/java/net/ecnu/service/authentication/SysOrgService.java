package net.ecnu.service.authentication;

import net.ecnu.model.authentication.SysOrg;
import net.ecnu.model.authentication.SysOrgNode;

import java.util.List;

public interface SysOrgService {

    List<SysOrgNode> getOrgTreeById(Integer rootOrgId,
                                    String orgNameLike,
                                    Boolean orgStatus);

    void updateOrg(SysOrg sysorg);

    void addOrg(SysOrg sysorg);

    void deleteOrg(SysOrg sysOrg);

    void updateStatus(Integer id, Boolean status);
}
