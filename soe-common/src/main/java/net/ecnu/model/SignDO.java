package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author LYW
 * @since 2023-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sign")
public class SignDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id：account_no
     */
    private Integer userId;

    /**
     * 累计总签到天数
     */
    private Integer totalDays;

    /**
     * 连续签到天数
     */
    private Integer continueDays;

    /**
     * 最后一次签到时间
     */
    private Date lastSign;

    /**
     * 可补签次数
     */
    private Integer resignNum;


}
