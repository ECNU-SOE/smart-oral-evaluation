package net.ecnu.model.authentication;

import net.ecnu.model.authentication.tree.DataTree;

import java.util.ArrayList;
import java.util.List;

public class SysMenuNode extends SysMenu implements DataTree<SysMenuNode,Integer> {

    private List<SysMenuNode> children = new ArrayList<>();

    private String path;
    private String name;

    public String getPath() {
        return this.getUrl();
    }

    public String getName() {
        return this.getMenuName();
    }

    @Override
    public Integer getParentId() {
        return super.getMenuPid();
    }

    @Override
    public void setChildren(List<SysMenuNode> children) {
        this.children = children;
    }

    @Override
    public List<SysMenuNode> getChildren() {
        return this.children;
    }
}
