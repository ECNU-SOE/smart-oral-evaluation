package net.ecnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.DiscussAudioDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 17:24
 */
@Mapper
public interface DiscussAudioMapper extends BaseMapper<DiscussAudioDo> {
    int deleteByPrimaryKey(Long audioId);

    int insert(DiscussAudioDo record);

    int insertSelective(DiscussAudioDo record);

    DiscussAudioDo selectByPrimaryKey(Long audioId);

    int updateByPrimaryKeySelective(DiscussAudioDo record);

    int updateByPrimaryKey(DiscussAudioDo record);
}