package net.ecnu.mapper;

import net.ecnu.model.CpsgrpDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 语料组(作业、试卷、测验) Mapper 接口
 * </p>
 *
 * @author LYW
 * @since 2022-11-02
 */
@Mapper
public interface CpsgrpMapper extends BaseMapper<CpsgrpDO> {

}
