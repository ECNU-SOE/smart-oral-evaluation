package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.CpsgrpVO;
import net.ecnu.model.vo.CpsrcdVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CpsgrpManagerImpl implements CpsgrpManager {

    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Autowired
    private CpsrcdManager cpsrcdManager;


    @Override
    public int insert(CpsgrpDO cpsgrpDO) {
        return cpsgrpMapper.insert(cpsgrpDO);
    }

    @Override
    public List<CpsgrpDO> listByFilter(CpsgrpFilterReq cpsgrpFilter, PageData pageData) {
        Page<CpsgrpDO> cpsgrpDOPage = cpsgrpMapper.selectPage(
                new Page<CpsgrpDO>(pageData.getCurrent(), pageData.getSize()), new QueryWrapper<>());
        return cpsgrpDOPage.getRecords();
    }

    @Override
    public int countByFilter(CpsgrpFilterReq cpsgrpFilter) {
        return cpsgrpMapper.selectCount(new QueryWrapper<CpsgrpDO>());
    }

    @Override
    public CpsgrpVO selectDetailByCpsgrpId(String cpsgrpId) {
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(cpsgrpId);
        if (cpsgrpDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        //处理cpsgrpVO题目组对象
        CpsgrpVO cpsgrpVO = new CpsgrpVO();
        BeanUtils.copyProperties(cpsgrpDO, cpsgrpVO);
        //处理cpsrcdVOS题目列表
        List<CpsrcdDO> cpsrcdDOS = cpsrcdManager.listByCpsgrpId(cpsgrpId);
        List<CpsrcdVO> cpsrcdVOS = cpsrcdDOS.stream().map(this::beanProcess).collect(Collectors.toList());
//        cpsgrpVO.setCpsrcdList(cpsrcdVOS);
        return cpsgrpVO;
    }

    /**
     * CpsrcdDO->CpsrcdVO
     */
    private CpsrcdVO beanProcess(CpsrcdDO cpsrcdDO) {
        CpsrcdVO cpsrcdVO = new CpsrcdVO();
        BeanUtils.copyProperties(cpsrcdDO, cpsrcdVO);
        return cpsrcdVO;
    }


}
