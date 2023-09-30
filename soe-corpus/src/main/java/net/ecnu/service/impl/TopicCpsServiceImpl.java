package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.controller.request.TopicCpsReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.TopicCpsDO;
import net.ecnu.mapper.TopicCpsMapper;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.service.TopicCpsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LYW
 * @since 2023-09-28
 */
@Service
public class TopicCpsServiceImpl extends ServiceImpl<TopicCpsMapper, TopicCpsDO> implements TopicCpsService {

    @Autowired
    private TopicCpsMapper topicCpsMapper;

    @Autowired
    private CpsrcdMapper cpsrcdMapper;

    @Autowired
    private CpsgrpServiceImpl cpsgrpService;

    @Override
    public CpsrcdVO addOne(TopicCpsReq topicCpsReq) {
        //数据校验
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(topicCpsReq.getCpsrcdId());
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
        List<TopicCpsDO> topicCpsDOS = topicCpsMapper.selectList(new QueryWrapper<TopicCpsDO>()
                .eq("topic_id", topicCpsReq.getTopicId()).eq("cpsrcd_id", topicCpsReq.getCpsrcdId()));
        if (!ObjectUtils.isEmpty(topicCpsDOS)) throw new BizException(BizCodeEnum.TOPIC_CPS_EXIST);

        //生成topic_cps对象插入表
        TopicCpsDO topicCpsDO = new TopicCpsDO();
        BeanUtils.copyProperties(topicCpsReq, topicCpsDO);
        int insert = topicCpsMapper.insert(topicCpsDO);

        //聚合cpsrcdVO类返回
        topicCpsDO = topicCpsMapper.selectOne(new QueryWrapper<TopicCpsDO>()
                .eq("topic_id", topicCpsReq.getTopicId()).eq("cpsrcd_id", topicCpsReq.getCpsrcdId()));
        return cpsgrpService.beanProcess(topicCpsDO);
    }

    @Override
    public int delOne(TopicCpsReq topicCpsReq) {
        return topicCpsMapper.delete(new QueryWrapper<TopicCpsDO>()
                .eq("topic_id", topicCpsReq.getTopicId()).eq("cpsrcd_id", topicCpsReq.getCpsrcdId()));
    }

    @Override
    public CpsrcdVO updateOne(TopicCpsReq topicCpsReq) {
        //校验数据
        TopicCpsDO topicCpsDO = topicCpsMapper.selectOne(new QueryWrapper<TopicCpsDO>()
                .eq("topic_id", topicCpsReq.getTopicId()).eq("cpsrcd_id", topicCpsReq.getCpsrcdId()));
        if (topicCpsDO == null) throw new BizException(BizCodeEnum.TOPIC_CPS_UNEXIST);
        //更新数据
        BeanUtils.copyProperties(topicCpsReq, topicCpsDO, "id");
        int i = topicCpsMapper.updateById(topicCpsDO);
        topicCpsDO = topicCpsMapper.selectById(topicCpsDO.getId());
        return cpsgrpService.beanProcess(topicCpsDO);
    }
}
