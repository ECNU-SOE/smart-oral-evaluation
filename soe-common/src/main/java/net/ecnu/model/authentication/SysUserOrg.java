package net.ecnu.model.authentication;

import lombok.Data;
import lombok.experimental.Accessors;
import net.ecnu.model.UserDO;

@Data
@Accessors(chain = true)
public class SysUserOrg extends UserDO {

    private String orgName;
}
