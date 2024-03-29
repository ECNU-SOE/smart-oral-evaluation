package net.ecnu.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String accountNo;
    private String identifyId;
    private String nickName;
    private String realName;
    private Integer firstLanguage;
    private Integer sex;
    private Date birth;
    private String sign;
    private String phone;
    private String mail;
    private Date gmtModified;
    private String pwd;
    private Integer orgId;
    private Boolean enabled;
}

