package net.ecnu.service.impl;

import net.ecnu.controller.request.CpsrcdTagReq;
import net.ecnu.controller.request.TagReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.TagManager;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.mapper.CpsrcdTagMapper;
import net.ecnu.model.CpsrcdTagDO;
import net.ecnu.model.TagDO;
import net.ecnu.mapper.TagMapper;
import net.ecnu.model.common.PageData;
import net.ecnu.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
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
    private CpsrcdTagMapper cpsrcdTagMapper;
    @Autowired
    private TagManager tagManager;

    @Override
    public Object create(TagReq tagReq) {
        TagDO tagDO = new TagDO();
        BeanUtils.copyProperties(tagReq,tagDO,"id");
        return tagMapper.insert(tagDO);
    }

    @Override
    public Object delete(Integer id) {
        TagDO tagDO = tagMapper.selectById(id);
        if (tagDO==null)
            return "标签不存在";
        return tagMapper.deleteById(id);
    }

    @Override
    public Object update(TagReq tagReq) {
        TagDO tagDO = new TagDO();
        BeanUtils.copyProperties(tagReq,tagDO);
        return tagMapper.updateById(tagDO);
    }

    @Override
    public Object list(TagReq tagReq, PageData pageData) {
        int i = tagManager.countByFilter(tagReq);
        pageData.setTotal(i);
        List<TagDO> tagDOS = tagManager.listByFilter(tagReq, pageData);
        pageData.setRecords(tagDOS);
        return pageData;
    }

    @Override
    public Object addTag(CpsrcdTagReq cpsrcdTagReq) {
        CpsrcdTagDO cpsrcdTagDO = new CpsrcdTagDO();
        BeanUtils.copyProperties(cpsrcdTagReq,cpsrcdTagDO);
        return cpsrcdTagMapper.insert(cpsrcdTagDO);
    }


}
