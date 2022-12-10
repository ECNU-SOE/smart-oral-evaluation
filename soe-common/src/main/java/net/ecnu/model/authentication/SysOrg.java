package net.ecnu.model.authentication;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysOrg extends BaseColumns {
    private Integer id;

    /**
     * 上级组织编码
     */
    private Integer orgPid;

    /**
     * 所有的父节点id
     */
    private String orgPids;

    /**
     * 0:不是叶子节点，1:是叶子节点
     */
    private Boolean isLeaf;

    /**
     * 组织名
     */
    private String orgName;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮件
     */
    private String email;

    /**
     * 排序
     */
    private Integer orgSort;

    /**
     * 组织层级
     */
    private Integer level;

    /**
     * 0:启用,1:禁用
     */
    private Boolean status;

}
