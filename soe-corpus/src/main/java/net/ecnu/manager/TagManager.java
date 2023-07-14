package net.ecnu.manager;


import net.ecnu.controller.request.TagReq;
import net.ecnu.model.TagDO;
import net.ecnu.model.TopicDO;
import net.ecnu.model.common.PageData;

import java.util.List;

public interface TagManager {
    int countByFilter(TagReq tagReq);

    List<TagDO> listByFilter(TagReq tagReq, PageData pageData);
}
