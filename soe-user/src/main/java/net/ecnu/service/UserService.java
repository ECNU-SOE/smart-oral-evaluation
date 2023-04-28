package net.ecnu.service;

import net.ecnu.controller.request.UserFilterReq;
import net.ecnu.controller.request.UserReq;
import net.ecnu.model.UserDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface UserService {

    Object register(UserReq userReq);

    Object login(UserReq userReq);

    boolean send(String phoneNum);

    Object getUserInfo();
    Object pageByFilter(UserFilterReq userFilterReq, PageData pageData);
    Object update(UserReq userReq);
    Object batch(MultipartFile excelFile) throws IOException;

}
