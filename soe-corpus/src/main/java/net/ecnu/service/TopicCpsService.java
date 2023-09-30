package net.ecnu.service;

import net.ecnu.controller.request.TopicCpsReq;
import net.ecnu.model.TopicCpsDO;
import com.baomidou.mybatisplus.extension.service.IService;
import net.ecnu.model.vo.CpsrcdVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYW
 * @since 2023-09-28
 */
public interface TopicCpsService extends IService<TopicCpsDO> {

    CpsrcdVO addOne(TopicCpsReq topicCpsReq);

    int delOne(TopicCpsReq topicCpsReq);

    CpsrcdVO updateOne(TopicCpsReq topicCpsReq);
}
