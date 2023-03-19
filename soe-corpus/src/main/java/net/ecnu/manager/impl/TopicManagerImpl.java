package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.manager.CorpusManager;
import net.ecnu.manager.TopicManager;
import net.ecnu.mapper.CorpusMapper;
import net.ecnu.mapper.TopicMapper;
import net.ecnu.model.CorpusDO;
import net.ecnu.model.TopicDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TopicManagerImpl implements TopicManager {

    @Autowired
    private TopicMapper topicMapper;


    @Override
    public List<TopicDO> listByTopicIds(List<String> topicIds) {
        return topicMapper.selectBatchIds(topicIds);
    }
}
