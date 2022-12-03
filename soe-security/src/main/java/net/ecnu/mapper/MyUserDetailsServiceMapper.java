package net.ecnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.MyUserDetails;
import net.ecnu.model.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface MyUserDetailsServiceMapper extends BaseMapper<UserDO> {

    //目前采用手机号作为用户名来进行登录，SQL暂时写死
    @Select("select phone as username,pwd as password,enabled from user where phone = #{username}")
    MyUserDetails findByUserName(@Param("username") String username);

    //根据手机号查询用户信息
    @Select("select account_no,nick_name,first_language,real_name,phone,mail,enabled from user where phone = #{phone} and del = 0")
    UserDO findUserInfoByPhone(@Param("phone") String phone);

    //根据username查询用户角色列表
    @Select("SELECT role_code\n" +
            "FROM sys_role r\n" +
            "LEFT JOIN user_role ur ON r.id = ur.role_id  AND r.status = 0\n" +
            "LEFT JOIN user u ON u.account_no = ur.account_no\n" +
            "WHERE u.phone = #{username}")
    List<String> findRoleByUserName(@Param("username") String username);

    //根据用户角色查询用户菜单权限
    @Select({
            "<script>",
            "SELECT url " ,
            "FROM sys_menu m " ,
            "LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id " ,
            "LEFT JOIN sys_role r ON r.id = rm.role_id ",
            "WHERE r.role_code IN ",
            "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'>",
            "#{roleCode}",
            "</foreach>",
            " AND m.status = 0",
            "</script>"
    })
    List<String> findMenuByRoleCodes(@Param("roleCodes") List<String> roleCodes);


    //根据用户角色查询用户接口访问权限
    @Select(
            "SELECT url \n" +
                    "FROM sys_api a \n" +
                    "LEFT JOIN sys_role_api ra ON a.id = ra.api_id \n" +
                    "LEFT JOIN sys_role r ON r.id = ra.role_id \n" +
                    "WHERE r.role_code = #{roleCode} \n" +
                    "AND a.status = 0"
    )
    List<String> findApiByRoleCode(@Param("roleCode") String roleCode);

    //检查数据库User表中该手机号是否已存在对应用户
    @Select("select count(1) from user u where u.phone = #{phone} and u.del = 0")
    Integer checkUserByPhone(@Param("phone")String phone);

    //给新增用户赋角色
    @Insert("insert into user_role values (#{roleId},#{accountNo})")
    Integer insertUserRole(@Param("roleId") Integer roleId,@Param("accountNo") String accountNo);


}
