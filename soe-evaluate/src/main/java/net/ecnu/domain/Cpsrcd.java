package net.ecnu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class Cpsrcd {
    private String id;
    private String cpsgrpId;
    @TableField(value="`order`")
    private int order;
    private int type;
    private int level;
    private String pinyin;
    private String refText;
    private Date gmtCreate;
    private Date gmtModified;

}
