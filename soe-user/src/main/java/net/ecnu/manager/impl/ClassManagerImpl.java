package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.ClassFilterReq;
import net.ecnu.manager.ClassManager;
import net.ecnu.mapper.ClassMapper;
import net.ecnu.model.ClassDO;
import net.ecnu.model.common.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ClassManagerImpl implements ClassManager {
    @Autowired
    private ClassMapper classMapper;

    @Override
    public List<ClassDO> pageByFilter(ClassFilterReq classFilter, PageData pageData) {
        return classMapper.selectPage(new Page<ClassDO>(pageData.getCurrent(), pageData.getSize()),
                new QueryWrapper<ClassDO>()
                        .eq("del", 0)
        ).getRecords();
    }

    @Override
    public int countByFilter(ClassFilterReq classFilter) {
        return classMapper.selectCount(new QueryWrapper<ClassDO>()
                .eq("del", 0)
        );
    }

    @Override
    public IPage<ClassDO> pageByFilterLYW(ClassFilterReq classFilterReq, Page<ClassDO> page) {
        return classMapper.selectPage(page, new QueryWrapper<ClassDO>().eq("del", 0));

    }
}
