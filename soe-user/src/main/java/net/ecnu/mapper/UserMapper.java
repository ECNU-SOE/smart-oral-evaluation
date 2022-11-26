package net.ecnu.mapper;

import net.ecnu.model.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author LYW
 * @since 2022-10-22
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

}
