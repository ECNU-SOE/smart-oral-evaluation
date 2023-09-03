package net.ecnu.service;

import net.ecnu.model.DiscussLike;
import net.ecnu.model.DiscussLikeExample;

import java.util.List;
/**
 * @description:
 * @Author lsy
 * @Date 2023/9/3 13:36
 */
public interface DiscussLikeService{
    
    long countByExample(DiscussLikeExample example);

    
    int deleteByExample(DiscussLikeExample example);

    
    int deleteByPrimaryKey(Long id);

    
    int insert(DiscussLike record);

    
    int insertSelective(DiscussLike record);

    
    List<DiscussLike> selectByExample(DiscussLikeExample example);

    
    DiscussLike selectByPrimaryKey(Long id);
    
    int updateByExampleSelective(DiscussLike record,DiscussLikeExample example);
    
    int updateByExample(DiscussLike record,DiscussLikeExample example);
    
    int updateByPrimaryKeySelective(DiscussLike record);

    int updateByPrimaryKey(DiscussLike record);

}
