package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;
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
@TableName("sign_log")
public class SignLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 签到奖励内容
     */
    private String signReward;

    /**
     * 签到时间
     */
    private LocalDate signDate;

    /**
     * 签到类型，1：签到，2：补签
     */
    private Integer signType;

    /**
     * 数据创建时间
     */
    private Date gmtCreate;


}
