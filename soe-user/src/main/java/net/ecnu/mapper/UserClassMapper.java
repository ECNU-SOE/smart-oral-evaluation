package net.ecnu.mapper;

import net.ecnu.model.UserClassDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户-课程 关系表 Mapper 接口
 * </p>
 *
 * @author TGX
 * @since 2023-04-10
 */
@Mapper
public interface UserClassMapper extends BaseMapper<UserClassDO> {

}
