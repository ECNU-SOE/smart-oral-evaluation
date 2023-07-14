package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.TagReq;
import net.ecnu.manager.TagManager;
import net.ecnu.mapper.TagMapper;
import net.ecnu.mapper.TopicMapper;
import net.ecnu.model.TagDO;
import net.ecnu.model.TopicDO;
import net.ecnu.model.common.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TagManagerImpl implements TagManager {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public int countByFilter(TagReq tagReq) {
        return tagMapper.selectCount(new QueryWrapper<TagDO>()
                .eq(!ObjectUtils.isEmpty(tagReq.getId()), "id", tagReq.getId())
                .eq(!ObjectUtils.isEmpty(tagReq.getName()), "name", tagReq.getName())
                .eq(!ObjectUtils.isEmpty(tagReq.getTime()), "time", tagReq.getTime())
                .eq(!ObjectUtils.isEmpty(tagReq.getWeight()), "weight", tagReq.getWeight())
                .eq(!ObjectUtils.isEmpty(tagReq.getCategory()), "category", tagReq.getCategory())
        );
    }

    @Override
    public List<TagDO> listByFilter(TagReq tagReq, PageData pageData) {
        Page<TagDO> tagDOPage = tagMapper.selectPage(
                new Page<TagDO>(pageData.getCurrent(), pageData.getSize()),
                new QueryWrapper<TagDO>()
                        .eq(!ObjectUtils.isEmpty(tagReq.getId()), "id", tagReq.getId())
                        .eq(!ObjectUtils.isEmpty(tagReq.getName()), "name", tagReq.getName())
                        .eq(!ObjectUtils.isEmpty(tagReq.getTime()), "time", tagReq.getTime())
                        .eq(!ObjectUtils.isEmpty(tagReq.getWeight()), "weight", tagReq.getWeight())
                        .eq(!ObjectUtils.isEmpty(tagReq.getCategory()), "category", tagReq.getCategory())
        );
        return tagDOPage.getRecords();
    }
}
