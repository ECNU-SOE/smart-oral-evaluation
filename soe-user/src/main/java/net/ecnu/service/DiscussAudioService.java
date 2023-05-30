package net.ecnu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.ecnu.model.DiscussAudioDo;

import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 17:24
 */
public interface DiscussAudioService extends IService<DiscussAudioDo> {
    
    int deleteByPrimaryKey(Long audioId);

    
    int insert(DiscussAudioDo record);

    
    int insertSelective(DiscussAudioDo record);

    
    DiscussAudioDo selectByPrimaryKey(Long audioId);

    
    int updateByPrimaryKeySelective(DiscussAudioDo record);

    
    int updateByPrimaryKey(DiscussAudioDo record);

    List<String> selectByDiscussId(Long discussId);
}
