package net.ecnu.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.controller.request.TopicReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.TopicManager;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.TopicDO;
import net.ecnu.mapper.TopicMapper;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.model.vo.TopicVO;
import net.ecnu.service.TopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private CpsrcdMapper cpsrcdMapper;

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

    @Override
    public Object getDetail(String topicId) {
        //检查topicId是否存在
        TopicDO topicDO = topicMapper.selectById(topicId);
        if (topicDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        //查询子题
        List<CpsrcdDO> cpsrcdDOS = cpsrcdMapper.selectList(new QueryWrapper<CpsrcdDO>().eq("topic_id", topicId));
        List<CpsrcdVO> cpsrcdVOS = cpsrcdDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        //聚合生成topicVO对象
        TopicVO topicVO = new TopicVO();
        BeanUtils.copyProperties(topicDO, topicVO);
        topicVO.setSubCpsrcds(cpsrcdVOS);
        return topicVO;
    }

    /**
     * CpsrcdDO->CpsrcdVO
     */
    private CpsrcdVO beanProcess(CpsrcdDO cpsrcdDO) {
        CpsrcdVO cpsrcdVO = new CpsrcdVO();
        BeanUtils.copyProperties(cpsrcdDO, cpsrcdVO);
        //tags单独处理String->List<String>
        List<String> tags = JSONUtil.parseArray(cpsrcdDO.getTags()).toList(String.class);
        cpsrcdVO.setTags(tags);
        return cpsrcdVO;
    }
}
