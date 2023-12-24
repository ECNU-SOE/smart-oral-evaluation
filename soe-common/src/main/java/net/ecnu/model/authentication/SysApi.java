package net.ecnu.model.authentication;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author Joshua
 * @description
 * @date 2023/12/24 16:45
 */
@Data
public class SysApi implements Serializable {
    private Long id;

    /**
     * 接口父ID（即接口分组）
     */
    private Long apiPid;

    /**
     * 当前接口的所有上级id（即所有上级分组）
     */
    private String apiPids;

    /**
     * 0：不是叶子节点，1：是叶子节点
     */
    private Boolean isLeaf;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口url
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
     * 是否禁用   0：启用，1：禁用
     */
    private Boolean status;

    /**
     * 本条记录创建人
     */
    private String createBy;

    /**
     * 本条记录创建时间
     */
    private Date createTime;

    /**
     * 本条记录修改人
     */
    private String updateBy;

    /**
     * 本条记录的修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}