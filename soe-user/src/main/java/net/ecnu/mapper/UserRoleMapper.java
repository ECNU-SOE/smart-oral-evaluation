package net.ecnu.mapper;

import net.ecnu.model.UserRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TGX
 * @since 2023-03-23
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    @Select("select role_id from user_role where account_no = #{accountNo}")
    List<String> getRoles(String accountNo);
}
