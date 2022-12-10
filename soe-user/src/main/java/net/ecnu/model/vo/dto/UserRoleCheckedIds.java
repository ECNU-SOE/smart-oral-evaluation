package net.ecnu.model.vo.dto;

import java.util.List;

public class UserRoleCheckedIds {

  private String username;

  private Integer userId;

  private List<Integer> checkedIds;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public List<Integer> getCheckedIds() {
    return checkedIds;
  }

  public void setCheckedIds(List<Integer> checkedIds) {
    this.checkedIds = checkedIds;
  }
}
