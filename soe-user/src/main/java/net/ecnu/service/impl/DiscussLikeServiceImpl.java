package net.ecnu.service.impl;

import net.ecnu.mapper.DiscussLikeMapper;
import net.ecnu.model.DiscussLike;
import net.ecnu.model.DiscussLikeExample;
import net.ecnu.service.DiscussLikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/9/3 13:39
 */
@Service
public class DiscussLikeServiceImpl implements DiscussLikeService {
    @Resource
    private DiscussLikeMapper discussLikeMapper;


    public long countByExample(DiscussLikeExample example) {
        return discussLikeMapper.countByExample(example);
    }


    public int deleteByExample(DiscussLikeExample example) {
        return discussLikeMapper.deleteByExample(example);
    }


    public int deleteByPrimaryKey(Long id) {
        return discussLikeMapper.deleteByPrimaryKey(id);
    }


    public int insert(DiscussLike record) {
        return discussLikeMapper.insert(record);
    }


    public int insertSelective(DiscussLike record) {
        return discussLikeMapper.insertSelective(record);
    }


    public List<DiscussLike> selectByExample(DiscussLikeExample example) {
        return discussLikeMapper.selectByExample(example);
    }


    public DiscussLike selectByPrimaryKey(Long id) {
        return discussLikeMapper.selectByPrimaryKey(id);
    }


    public int updateByExampleSelective(DiscussLike record,DiscussLikeExample example) {
        return discussLikeMapper.updateByExampleSelective(record,example);
    }


    public int updateByExample(DiscussLike record,DiscussLikeExample example) {
        return discussLikeMapper.updateByExample(record,example);
    }


    public int updateByPrimaryKeySelective(DiscussLike record) {
        return discussLikeMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(DiscussLike record) {
        return discussLikeMapper.updateByPrimaryKey(record);
    }
}
