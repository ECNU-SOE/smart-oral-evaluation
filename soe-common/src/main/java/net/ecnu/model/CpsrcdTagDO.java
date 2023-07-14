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
 * @since 2023-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cpsrcd_tag")
public class CpsrcdTagDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer tagId;

    private String cpsrcdId;

    private Date gmtCreate;


}
