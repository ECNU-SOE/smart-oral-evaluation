package net.ecnu;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.domain.Corpus;
import net.ecnu.mapper.CorpusDao;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EvaluateApplication.class)
@Slf4j
public class SOETest {

    @Autowired
    private CorpusDao corpusDao;
    @Test
    public void testGetByPageAndCondition(){
        //lambda格式的querywrapper
        LambdaQueryWrapper<Corpus> lqw = new LambdaQueryWrapper<Corpus>();
        lqw.eq(Corpus::getType,0);
        IPage page = new Page(1,2);//1.当前页码 2.每页条数
        corpusDao.selectPage(page, lqw);
        System.out.println("当前页码："+page.getCurrent());
        System.out.println("每页条数："+page.getSize());
        System.out.println("一共多少页："+page.getPages());
        System.out.println("一共多少条数据："+page.getTotal());
        System.out.println("数据："+page.getRecords());
    }
}
