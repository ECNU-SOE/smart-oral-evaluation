package net.ecnu.service.impl;

import net.ecnu.controller.request.TopicReq;
import net.ecnu.manager.TopicManager;
import net.ecnu.model.TopicDO;
import net.ecnu.mapper.TopicMapper;
import net.ecnu.service.TopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LYW
 * @since 2023-03-17
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, TopicDO> implements TopicService {

    @Autowired
    private TopicManager topicManager;

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public Object add(TopicReq topicReq) {
        TopicDO topicDO = new TopicDO();
        BeanUtils.copyProperties(topicReq, topicDO);
        topicDO.setId(IDUtil.nextTopicId());
        int rows = topicMapper.insert(topicDO);
        TopicDO topic = topicMapper.selectById(topicDO.getId());
        return topic;
    }

    @Override
    public Object del(String topicId) {
        int rows = topicMapper.deleteById(topicId);
        return rows;
    }

    @Override
    public Object modify(TopicReq topicReq) {
        TopicDO topicDO = topicMapper.selectById(topicReq.getId());
        BeanUtils.copyProperties(topicReq, topicDO, "id", "cpsgrpId");
        int i = topicMapper.updateById(topicDO);
        TopicDO topic = topicMapper.selectById(topicReq.getId());
        return topic;
    }
}
