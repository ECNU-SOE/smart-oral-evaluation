package net.ecnu.model.authentication;

import net.ecnu.model.authentication.tree.DataTree;

import java.util.List;

public class SysApiNode extends SysApi implements DataTree<SysApiNode,Long> {

    private List<SysApiNode> children;

    @Override
    public Long getParentId() {
        return super.getApiPid();
    }

    @Override
    public void setChildren(List<SysApiNode> children) {
        this.children = children;
    }

    @Override
    public List<SysApiNode> getChildren() {
        return this.children;
    }
}
