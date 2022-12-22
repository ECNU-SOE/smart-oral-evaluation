package net.ecnu.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.model.authentication.SysApi;
import net.ecnu.model.authentication.SysMenu;
import net.ecnu.model.authentication.SysOrg;
import net.ecnu.model.authentication.SysUserOrg;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SystemMapper {

    List<SysOrg> selectOrgTree(@Param("rootOrgId") Integer rootOrgId,
                               @Param("orgNameLike") String orgNameLike,
                               @Param("orgStatus") Boolean orgStatus);

    List<SysMenu> selectMenuTree(@Param("rootMenuId") Integer rootMenuId,
                                 @Param("menuNameLike") String menuNameLike,
                                 @Param("menuStatus") Boolean menuStatus);

    //用户管理-查询用户
    IPage<SysUserOrg> selectUser(Page<SysUserOrg> page,
                                 @Param("realName") String realName,
                                 @Param("orgId") Integer orgId,
                                 @Param("phone") String phone,
                                 @Param("email") String email,
                                 @Param("enabled") Boolean enabled,
                                 @Param("createStartTime") Date createStartTime,
                                 @Param("createEndTime") Date createEndTime);

    List<SysApi> selectApiTree(@Param("rootApiId") Long rootApiId,
                               @Param("apiNameLike") String apiNameLike,
                               @Param("apiStatus") Boolean apiStatus);

    List<String> selectApiExpandedKeys();

    List<String> selectApiCheckedKeys(Integer roleId);

    Integer insertRoleApiIds(@Param("roleId") Integer roleId,
                             @Param("checkedIds") List<Integer> checkedIds);

    List<String> getCheckedRoleIds(@Param("accountNo") String accountNo);

    Integer insertUserRoleIds(@Param("accountNo") String accountNo,
                              @Param("checkedIds") List<Integer> checkedIds);

    List<String> selectMenuCheckedKeys(Integer roleId);

    List<String> selectMenuExpandedKeys();

    Integer insertRoleMenuIds(@Param("roleId") Integer roleId,
                              @Param("checkedIds") List<Integer> checkedIds);

    List<SysMenu> selectMenuByUsername(@Param("accountNo") String accountNo);
}
