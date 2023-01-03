package net.ecnu.service.authentication;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.ecnu.model.UserDO;
import net.ecnu.model.authentication.SysUserOrg;
import net.ecnu.model.dto.UserDTO;
import net.ecnu.util.JsonData;

import java.util.Date;

public interface SysUserService {

    //用户管理：查询
    IPage<SysUserOrg> queryUser(Integer orgId,
                                String realName,
                                String phone,
                                String email,
                                Boolean enabled,
                                Date createStartTime,
                                Date createEndTime,
                                Integer pageNum,
                                Integer pageSize);

    UserDO getUserByUserName(String accountNo);

    void updateUser(UserDTO sysuser);

    void addUser(UserDTO sysuser);

    void deleteUser(String accountNo);

    void pwdreset(String accountNo);

    void updateEnabled(String accountNo, Boolean enabled);

    void changePwd(String accountNo, String oldPass, String newPass);

    JsonData isdefault(String accountNo);
}
