package net.ecnu.db;


import lombok.extern.slf4j.Slf4j;
import net.ecnu.UserApplication;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class SOETest {


    @Test
    public void pinyinTest() {

    }
}
