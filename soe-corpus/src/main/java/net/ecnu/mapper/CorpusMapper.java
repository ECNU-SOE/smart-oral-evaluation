package net.ecnu.mapper;

import net.ecnu.model.CorpusDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 语料原型 Mapper 接口
 * </p>
 *
 * @author LYW
 * @since 2022-10-20
 */
@Mapper
public interface CorpusMapper extends BaseMapper<CorpusDO> {

    @Select("select * from corpus order by rand() limit 1")
    CorpusDO getRandomCorpus();
}
