package net.ecnu.mapper;

import net.ecnu.model.TranscriptDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 成绩单 Mapper 接口
 * </p>
 *
 * @author LYW
 * @since 2022-11-19
 */
@Mapper
public interface TranscriptMapper extends BaseMapper<TranscriptDO> {

}
