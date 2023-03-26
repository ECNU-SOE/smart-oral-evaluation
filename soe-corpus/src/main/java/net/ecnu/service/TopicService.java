package net.ecnu.service;

import net.ecnu.controller.request.TopicReq;
import net.ecnu.model.TopicDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYW
 * @since 2023-03-17
 */
public interface TopicService extends IService<TopicDO> {

    Object add(TopicReq topicReq);

    Object del(String topicId);

    Object modify(TopicReq topicReq);
}
