package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.controller.request.TagFilterReq;
import net.ecnu.controller.request.TaggingReq;
import net.ecnu.controller.request.TagReq;
import net.ecnu.manager.TagManager;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.mapper.TaggingMapper;
import net.ecnu.model.TaggingDO;
import net.ecnu.model.TagDO;
import net.ecnu.mapper.TagMapper;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.TagVO;
import net.ecnu.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LYW
 * @since 2023-07-14
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CpsrcdMapper cpsrcdMapper;
    @Autowired
    private TaggingMapper taggingMapper;
    @Autowired
    private TagManager tagManager;

    @Override
    public Object create(TagReq tagReq) {
        TagDO tagDO1 = tagMapper.selectOne(new QueryWrapper<TagDO>()
                .eq("name", tagReq.getName())
        );
        if (tagDO1 != null)
            return "重复添加";
        TagDO tagDO = new TagDO();
        BeanUtils.copyProperties(tagReq, tagDO, "id","weight");
        tagDO.setWeight(0.00);
        return tagMapper.insert(tagDO);
    }

    @Override
    public Object delete(Integer id) {
        TagDO tagDO = tagMapper.selectById(id);
        if (tagDO == null)
            return "标签不存在";
        List<TaggingDO> taggingDOS = taggingMapper.selectList(new QueryWrapper<TaggingDO>()
                .eq("tag_id", id)
        );
        if (!CollectionUtils.isEmpty(taggingDOS))
            return "标签在使用中";
        return tagMapper.deleteById(id);
    }

    @Override
    public Object update(TagReq tagReq) {
        TagDO tagDO = new TagDO();
        BeanUtils.copyProperties(tagReq, tagDO,"weight");
        return tagMapper.updateById(tagDO);
    }



    @Override
    public Object list(TagFilterReq tagFilterReq, PageData pageData) {
        if (CollectionUtils.isEmpty(tagFilterReq.getIds())){
            //条件查询
            List<TagDO> tagDOS = tagManager.pageByFilter(tagFilterReq,pageData);
            List<TagVO> tagVOS = tagDOS.stream().map(this::beanProcess)
                    .sorted(Comparator.comparing(TagVO::getFrequency).reversed()).collect(Collectors.toList());
            int total = tagManager.countByFilter(tagFilterReq);
            pageData.setTotal(total);
            pageData.setRecords(tagVOS);
            return pageData;
        }
        else {
            //按照tagIds查询
            List<TagDO> tagDOS = tagMapper.selectBatchIds(tagFilterReq.getIds());
            List<TagVO> tagVOS = tagDOS.stream().map(this::beanProcess)
                    .sorted(Comparator.comparing(TagVO::getFrequency).reversed()).collect(Collectors.toList());
            pageData.setTotal(tagDOS.size());
            pageData.setRecords(tagVOS);
            return pageData;
        }
    }

    private TagVO beanProcess(TagDO tagDO) {
        TagVO tagVO = new TagVO();
        Integer count = taggingMapper.selectCount(new QueryWrapper<TaggingDO>().eq("tag_id", tagDO.getId()));
        tagVO.setFrequency(count);
        BeanUtils.copyProperties(tagDO,tagVO);
        return tagVO;
    }

    @Override
    public Object addTagging(TaggingReq taggingReq) {
        TaggingDO taggingDO1 = taggingMapper.selectOne(new QueryWrapper<TaggingDO>()
                .eq("tag_id", taggingReq.getTagId())
                .eq("entity_id", taggingReq.getEntityId())
        );
        if (taggingDO1 != null)
            return "关联标签已存在";
        TaggingDO taggingDO = new TaggingDO();
        BeanUtils.copyProperties(taggingReq, taggingDO);
        int i = taggingMapper.insert(taggingDO);
        return i;

    }

    @Override
    public Object listEntityTags(String entityId) {
        List<TaggingDO> taggingDOS = taggingMapper.selectList(new QueryWrapper<TaggingDO>()
                .eq("entity_id", entityId)
        );
        if (taggingDOS.size()==0)
            return "该对象暂无标签";
        List<Integer> tagIds = taggingDOS.stream().map(TaggingDO::getTagId).collect(Collectors.toList());
        List<TagDO> tagDOS = tagMapper.selectBatchIds(tagIds);
        return tagDOS;
    }

    @Override
    public Object delTagging(TaggingReq taggingReq) {
        TagDO tagDO = tagMapper.selectOne(new QueryWrapper<TagDO>()
                .eq("name", taggingReq.getTagName())
        );
        TaggingDO taggingDO = taggingMapper.selectOne(new QueryWrapper<TaggingDO>()
                .eq("tag_id", tagDO.getId())
                .eq("entity_id", taggingReq.getEntityId())
        );
        if (taggingDO==null)
            return "该实体所对应的标签不存在";
        int i = taggingMapper.deleteById(taggingDO.getId());
        return "删除成功，共影响了"+i+"行";
    }

    @Scheduled(cron = "59 59 23 * * ?")//在每天的23时59分59秒自动执行任务
    @Override
    public void calWeight() {
        Integer total = taggingMapper.selectCount(null);
        if (total == 0)
            return;
        List<TagDO> tagDOS = tagMapper.selectList(null);
        tagDOS.forEach(this::updateTagWeight);
        System.out.println(LocalDate.now()+"，标签权重计算成功");
    }


    private void updateTagWeight(TagDO tagDO){
        Integer total = taggingMapper.selectCount(null);
        if (total == 0)
            return ;
        Integer count = taggingMapper.selectCount(new QueryWrapper<TaggingDO>()
                .eq("tag_id", tagDO.getId())
        );
        TagDO newTagDO = new TagDO();
        BeanUtils.copyProperties(tagDO,newTagDO,"weight");
        double result = count / (double)total;
        DecimalFormat formatter = new DecimalFormat("#.0000");
        double weight = Double.parseDouble(formatter.format(result));
        newTagDO.setWeight(weight);
        tagMapper.updateById(newTagDO);
    }





}
