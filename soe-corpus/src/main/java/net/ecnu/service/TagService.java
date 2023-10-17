package net.ecnu.service;

import net.ecnu.controller.request.TagFilterReq;
import net.ecnu.controller.request.TaggingReq;
import net.ecnu.controller.request.TagReq;
import net.ecnu.model.TagDO;
import com.baomidou.mybatisplus.extension.service.IService;
import net.ecnu.model.common.PageData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYW
 * @since 2023-07-14
 */
public interface TagService extends IService<TagDO> {

    Object create(TagReq tagReq);
    Object delete(Integer id);
    Object update(TagReq tagReq);
    Object list(TagFilterReq tagFilterReq, PageData pageData);
    Object addTagging(TaggingReq taggingReq);
    Object listEntityTags(String entityId);
    Object delTagging(TaggingReq taggingReq);
    Object calWeight();

}
