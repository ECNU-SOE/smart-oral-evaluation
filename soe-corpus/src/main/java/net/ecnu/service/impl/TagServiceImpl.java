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
import net.ecnu.model.UserDO;
import net.ecnu.model.common.PageData;
import net.ecnu.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        BeanUtils.copyProperties(tagReq, tagDO, "id");
        return tagMapper.insert(tagDO);
    }

    @Override
    public Object delete(Integer id) {
        TagDO tagDO = tagMapper.selectById(id);
        if (tagDO == null)
            return "标签不存在";
        return tagMapper.deleteById(id);
    }

    @Override
    public Object update(TagReq tagReq) {
        TagDO tagDO = new TagDO();
        BeanUtils.copyProperties(tagReq, tagDO);
        return tagMapper.updateById(tagDO);
    }



    @Override
    public Object list(TagFilterReq tagFilterReq, PageData pageData) {
        if (tagFilterReq.getIds()==null||tagFilterReq.getIds().size()==0){
            //条件查询
            List<TagDO> tagDOS = tagManager.pageByFilter(tagFilterReq,pageData);
            int total = tagManager.countByFilter(tagFilterReq);
            pageData.setTotal(total);
            pageData.setRecords(tagDOS);
            return pageData;
        }
        else {
            //按照tagIds查询
            List<TagDO> tagDOS = tagMapper.selectBatchIds(tagFilterReq.getIds());
            pageData.setTotal(tagDOS.size());
            pageData.setRecords(tagDOS);
            return pageData;
        }
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
        return taggingMapper.insert(taggingDO);

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


}