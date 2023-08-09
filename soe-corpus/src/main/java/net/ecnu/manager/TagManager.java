package net.ecnu.manager;


import net.ecnu.controller.request.TagFilterReq;
import net.ecnu.controller.request.TagReq;
import net.ecnu.model.TagDO;
import net.ecnu.model.TopicDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface TagManager {
    int countByFilter(TagFilterReq tagFilterReq);

    List<TagDO> pageByFilter(TagFilterReq tagFilterReq, PageData pageData);
}
