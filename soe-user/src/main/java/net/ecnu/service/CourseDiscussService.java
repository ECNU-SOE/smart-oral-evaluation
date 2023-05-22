package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.model.CourseDiscussDo;
import net.ecnu.model.dto.DiscussDto;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 16:22
 */
public interface CourseDiscussService {

    
    int deleteByPrimaryKey(Long discussId);

    
    int insert(CourseDiscussDo record);

    
    int insertSelective(CourseDiscussDo record);

    
    CourseDiscussDo selectByPrimaryKey(Long discussId);

    
    int updateByPrimaryKeySelective(CourseDiscussDo record);

    
    int updateByPrimaryKey(CourseDiscussDo record);

    int addLikes(String discussId);

    Boolean addDiscuss(DiscussDto discussDto);

    Page<CourseDiscussDo> getDiscussInfo(String classId, Integer pageNum, Integer pageSize);

    Page<CourseDiscussDo> getReplyInfo(String discussId, Integer pageNum, Integer pageSize);

    void reply(DiscussDto discussDto);
}
