package net.ecnu;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EvaluateApplication.class)
@Slf4j
public class SOETest {

    @Test
    public void evaluateTest() {
        System.out.println("hello world");
    }
}
