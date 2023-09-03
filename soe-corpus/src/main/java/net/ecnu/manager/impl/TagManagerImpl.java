package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.TagFilterReq;
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
    public int countByFilter(TagFilterReq tagFilterReq) {
        return tagMapper.selectCount(new QueryWrapper<TagDO>()
                .like(!ObjectUtils.isEmpty(tagFilterReq.getName()), "name", tagFilterReq.getName())
                .eq(!ObjectUtils.isEmpty(tagFilterReq.getWeight()), "weight", tagFilterReq.getWeight())
                .eq(!ObjectUtils.isEmpty(tagFilterReq.getCategory()), "category", tagFilterReq.getCategory())
        );
    }

    @Override
    public List<TagDO> pageByFilter(TagFilterReq tagFilterReq, PageData pageData) {
        Page<TagDO> tagDOPage = tagMapper.selectPage(
                new Page<TagDO>(pageData.getCurrent(), pageData.getSize()),
                new QueryWrapper<TagDO>()
                        .like(!ObjectUtils.isEmpty(tagFilterReq.getName()), "name", tagFilterReq.getName())
                        .eq(!ObjectUtils.isEmpty(tagFilterReq.getWeight()), "weight", tagFilterReq.getWeight())
                        .eq(!ObjectUtils.isEmpty(tagFilterReq.getCategory()), "category", tagFilterReq.getCategory())
        );
        return tagDOPage.getRecords();
    }
}
