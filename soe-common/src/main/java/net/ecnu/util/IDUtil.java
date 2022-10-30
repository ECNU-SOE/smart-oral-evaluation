package net.ecnu.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;


public class IDUtil {

    static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    /**
     * 雪花算法生成id
     */
    public static long getSnowflakeId() {
        return snowflake.nextId();
    }
}
