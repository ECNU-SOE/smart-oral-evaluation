package net.ecnu.service;

import net.ecnu.controller.request.SignReq;
import net.ecnu.controller.request.UserFilterReq;
import net.ecnu.controller.request.UserReq;
import net.ecnu.model.common.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface UserService {

    Object register(UserReq userReq);

    Object login(UserReq userReq);

    boolean send(String phoneNum);

    Object getUserInfo();
    Object pageByFilter(UserFilterReq userFilterReq, PageData pageData);
    Object update(UserReq userReq);
    Object batch(MultipartFile excelFile) throws IOException;
    //获取角色的最高角色身份
    Integer getTopRole(String accountNo);
    Object del(String accountNo);
    Object sign();
    Object resign(LocalDate resignDate);
    Object signInfo(Integer year,Integer month);
    Object getName(String accountNo);
}
