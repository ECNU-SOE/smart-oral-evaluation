package net.ecnu.manager;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.model.CorpusDO;
import net.ecnu.model.TopicDO;

import java.util.List;

public interface TopicManager {
    List<TopicDO> listByTopicIds(List<String> topicIds);
}
