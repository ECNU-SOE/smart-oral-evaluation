package net.ecnu.aop.annotate;

import java.lang.annotation.*;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/3 21:57
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenAnalysis {
}
