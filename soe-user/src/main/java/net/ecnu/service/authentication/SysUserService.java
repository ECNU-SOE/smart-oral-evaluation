package net.ecnu.service.authentication;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.ecnu.model.UserDO;
import net.ecnu.model.authentication.SysUserOrg;
import net.ecnu.model.vo.dto.UserDTO;

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

    void pwdreset(String phone);

    void updateEnabled(String phone, Boolean enabled);

    void changePwd(String phone, String oldPass, String newPass);

    Boolean isdefault(String accountNo);
}
