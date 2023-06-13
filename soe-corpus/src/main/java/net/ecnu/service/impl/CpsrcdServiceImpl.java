package net.ecnu.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import net.ecnu.controller.request.CpsrcdReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.mapper.TopicMapper;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.model.TopicDO;
import net.ecnu.service.CpsrcdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.service.OssService;
import net.ecnu.util.IDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.List;

/**
 * <p>
 * corpus快照 服务实现类
 * </p>
 *
 * @author LYW
 * @since 2023-03-17
 */
@Service
public class CpsrcdServiceImpl extends ServiceImpl<CpsrcdMapper, CpsrcdDO> implements CpsrcdService {

    @Autowired
    private CpsrcdManager cpsrcdManager;

    @Autowired
    private CpsrcdMapper cpsrcdMapper;

    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private OssService ossService;

    @Override
    public Object add(CpsrcdReq cpsrcdReq) {
        //数据合法性校验
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(cpsrcdReq.getCpsgrpId());
        TopicDO topicDO = topicMapper.selectById(cpsrcdReq.getTopicId());
        if (cpsgrpDO == null || topicDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        //向cpsrcd表中插入新记录
        CpsrcdDO cpsrcdDO = new CpsrcdDO();
        BeanUtils.copyProperties(cpsrcdReq, cpsrcdDO);
        cpsrcdDO.setTags(JSONUtil.toJsonStr(cpsrcdReq.getTags()));
        cpsrcdDO.setId(IDUtil.nextCpsrcdId());
        int insert = cpsrcdMapper.insert(cpsrcdDO);
        cpsrcdDO = cpsrcdMapper.selectById(cpsrcdDO.getId());
        return cpsrcdDO;
    }

    @Override
    public Object mod(CpsrcdReq cpsrcdReq) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdReq.getId());
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        //全量更新 但不会更新null值
        BeanUtils.copyProperties(cpsrcdReq, cpsrcdDO, "cpsgrpId");
        cpsrcdDO.setTags(JSONUtil.toJsonStr(cpsrcdReq.getTags()));
        cpsrcdDO.setGmtModified(null);//Mysql会自动更新时间
        int i = cpsrcdMapper.updateById(cpsrcdDO);
        cpsrcdDO = cpsrcdMapper.selectById(cpsrcdReq.getId());
//        List<String> list = JSONUtil.toList(cpsrcdDO.getTags(), String.class);
        return cpsrcdDO;
    }

    @Override
    public Object del(String cpsrcdId) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdId);
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        int i = cpsrcdMapper.deleteById(cpsrcdId);
        return i;
    }


}
