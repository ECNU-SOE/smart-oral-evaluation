package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.model.ClassDiscussDo;
import net.ecnu.model.dto.DiscussDto;
import net.ecnu.model.dto.ForwardDto;
import net.ecnu.model.dto.ReplyInfoReq;
import net.ecnu.model.vo.DiscussVo;
import net.ecnu.model.vo.ReplyInfoVo;
import net.ecnu.util.JsonData;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 16:22
 */
public interface ClassDiscussService {

    
    int deleteByPrimaryKey(Long discussId);

    
    int insert(ClassDiscussDo record);

    
    int insertSelective(ClassDiscussDo record);

    
    ClassDiscussDo selectByPrimaryKey(Long discussId);

    
    int updateByPrimaryKeySelective(ClassDiscussDo record);

    
    int updateByPrimaryKey(ClassDiscussDo record);

    JsonData addLikes(String discussId, Integer likeFlag);

    Boolean addDiscuss(DiscussDto discussDto);

    Page<DiscussVo> getDiscussInfo(String classId, Integer pageNum, Integer pageSize);

    Page<DiscussVo> getDiscussInfo(ReplyInfoReq replyInfoReq);

    Page<ReplyInfoVo> getReplyInfo(String discussId, Integer pageNum, Integer pageSize);

    void reply(DiscussDto discussDto);

    int forward(ForwardDto forwardDto);

    int topDiscuss(Long discussId);
}
