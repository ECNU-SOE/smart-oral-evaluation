package net.ecnu.model.authentication;

import net.ecnu.model.authentication.tree.DataTree;

import java.util.List;

public class SysOrgNode extends SysOrg implements DataTree<SysOrgNode,Integer> {

    private List<SysOrgNode> children;

    @Override
    public Integer getParentId() {
        return super.getOrgPid();
    }

    @Override
    public void setChildren(List<SysOrgNode> children) {
        this.children = children;
    }

    @Override
    public List<SysOrgNode> getChildren() {
        return this.children;
    }
}
