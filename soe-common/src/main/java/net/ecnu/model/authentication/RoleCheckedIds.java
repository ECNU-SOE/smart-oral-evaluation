package net.ecnu.model.authentication;

import java.util.List;

public class RoleCheckedIds {

  private String roleCode;

  private Integer roleId;

  private List<Integer> checkedIds;

  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public List<Integer> getCheckedIds() {
    return checkedIds;
  }

  public void setCheckedIds(List<Integer> checkedIds) {
    this.checkedIds = checkedIds;
  }
}
