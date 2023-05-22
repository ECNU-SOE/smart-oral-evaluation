package net.ecnu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.mapper.DiscussAudioMapper;
import net.ecnu.model.DiscussAudioDo;
import net.ecnu.service.DiscussAudioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 17:26
 */
@Service
public class DiscussAudioServiceImpl extends ServiceImpl<DiscussAudioMapper,DiscussAudioDo> implements DiscussAudioService {

    @Resource
    private DiscussAudioMapper discussAudioMapper;


    public int deleteByPrimaryKey(Long audioId) {
        return discussAudioMapper.deleteByPrimaryKey(audioId);
    }


    public int insert(DiscussAudioDo record) {
        return discussAudioMapper.insert(record);
    }


    public int insertSelective(DiscussAudioDo record) {
        return discussAudioMapper.insertSelective(record);
    }


    public DiscussAudioDo selectByPrimaryKey(Long audioId) {
        return discussAudioMapper.selectByPrimaryKey(audioId);
    }


    public int updateByPrimaryKeySelective(DiscussAudioDo record) {
        return discussAudioMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(DiscussAudioDo record) {
        return discussAudioMapper.updateByPrimaryKey(record);
    }

}
