package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.mapper.DiscussAudioMapper;
import net.ecnu.model.DiscussAudioDo;
import net.ecnu.service.DiscussAudioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<String> selectByDiscussId(Long discussId) {
        List<String> discussAudioUrlList = discussAudioMapper.getAudioUrlByDiscussId(discussId);
        return discussAudioUrlList;
    }

}
