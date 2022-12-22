package net.ecnu.model.authentication;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysApi extends BaseColumns {

    @TableId(value ="id",type = IdType.AUTO)
    private Long id;

    /**
     * 接口父ID(即接口分组)
     */
    private Long apiPid;

    /**
     * 当前接口的所有上级id(即所有上级分组)
     */
    private String apiPids;

    /**
     * 0:不是叶子节点，1:是叶子节点
     */
    private Boolean isLeaf;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 跳转URL
     */
    private String url;

    /**
     * 排序
     */
    private Integer apiSort;

    /**
     * 层级，1：接口分组，2：接口
     */
    private Integer level;

    /**
     * 是否禁用，0:启用(否）,1:禁用(是)
     */
    private Boolean status;

}