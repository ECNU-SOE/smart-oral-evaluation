package net.ecnu.mapper;

import net.ecnu.model.CpsrcdDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * corpus快照 Mapper 接口
 * </p>
 *
 * @author LYW
 * @since 2022-11-03
 */
@Mapper
public interface CpsrcdMapper extends BaseMapper<CpsrcdDO> {
}
