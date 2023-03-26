package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CourFilterReq;
import net.ecnu.manager.CourseManager;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.mapper.CourseMapper;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.model.CourseDO;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.common.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CpsgrpManagerImpl implements CpsgrpManager {
    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Override
    public List<CpsgrpDO> pageByFilter(CpsgrpDO cpsgrpDO, PageData pageData) {
        List<CpsgrpDO> records = cpsgrpMapper.selectPage(new Page<CpsgrpDO>(pageData.getCurrent(), pageData.getSize()),
                new QueryWrapper<CpsgrpDO>()
        ).getRecords();
        records.removeIf(Objects::isNull);
        return records;
    }

    @Override
    public int countByFilter(CpsgrpDO cpsgrpDO) {
        return cpsgrpMapper.selectCount(new QueryWrapper<CpsgrpDO>()
        );
    }
}
