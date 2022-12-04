package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.CpsgrpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CpsgrpManagerImpl implements CpsgrpManager {

    @Autowired
    private CpsgrpMapper cpsgrpMapper;



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


}
