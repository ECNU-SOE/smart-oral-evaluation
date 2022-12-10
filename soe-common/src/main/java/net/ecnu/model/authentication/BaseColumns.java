package net.ecnu.model.authentication;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据库表通用字段
 **/
@Data
public class BaseColumns {

  /**
   * 本条记录创建人
   */
  @TableField(fill = FieldFill.INSERT,select = false)
  private String createBy;

  /**
   * 本条记录创建时间
   */
  @TableField(fill = FieldFill.INSERT,select = false)
  private LocalDateTime  createTime;

  /**
   * 本条记录更新人
   */
  @TableField(fill = FieldFill.INSERT_UPDATE,select = false)
  private String updateBy;

  /**
   * 本条记录更新时间
   */
  @TableField(fill = FieldFill.INSERT_UPDATE,select = false)
  private LocalDateTime updateTime;


}
