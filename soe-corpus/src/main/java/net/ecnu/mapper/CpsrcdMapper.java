package net.ecnu.mapper;

import net.ecnu.model.CorpusDO;
import net.ecnu.model.CpsrcdDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Select("select * from cpsrcd order by rand() limit 1")
    CpsrcdDO getRandomCpsrcd();
}
