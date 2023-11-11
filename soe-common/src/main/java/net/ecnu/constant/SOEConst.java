package net.ecnu.constant;


import java.util.regex.Pattern;

public class SOEConst {

    /**
     * 手机号正则表达式
     */
    public static final String PHONE_PATTERN = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";

    /**
     * 邮箱正则表达式
     */
    public static final String MAIL_PATTERN = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 语音分片大小
     */
    public static final int PKG_SIZE = 100 * 1024;

    /**
     * 默认错字得分线
     */
    public static final Double ERR_SCORE_LINE = 90.0;

    /**
     * 用户初始密码
     */
    public static final String USER_INIT_PASSWORD = "abcd1234";

    public static final Integer SUCCESS = 0;

}
