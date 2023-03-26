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

    public static String nextTopicId() {
        return "topic_" + getSnowflakeId();
    }

    public static String nextCpsrcdId() {
        return "cpsrcd_" + getSnowflakeId();
    }

    public static String nextCpsgrpId() {
        return "cpsgrp_" + getSnowflakeId();
    }

    public static String nextCourseId() { return "course_" + getSnowflakeId(); }
}
