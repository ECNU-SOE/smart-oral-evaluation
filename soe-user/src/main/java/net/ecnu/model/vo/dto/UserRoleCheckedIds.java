package net.ecnu.model.vo.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleCheckedIds {


  private String accountNo;

  private List<Integer> checkedIds;

}
