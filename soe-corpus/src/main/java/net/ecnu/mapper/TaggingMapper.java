package net.ecnu.mapper;

import net.ecnu.model.TaggingDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYW
 * @since 2023-07-14
 */
@Mapper
public interface TaggingMapper extends BaseMapper<TaggingDO> {

    List<String> getTagInfoByCpsrcdId(@Param("cpsrcdId") String cpsrcdId);
}
