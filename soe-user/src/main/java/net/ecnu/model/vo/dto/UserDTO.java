package net.ecnu.model.vo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
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
}
