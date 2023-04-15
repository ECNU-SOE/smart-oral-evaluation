package net.ecnu.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * corpus快照
 * </p>
 *
 * @author LYW
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CpsgrpVO implements Serializable {

    /**
     * 语料组id
     */
    private String id;

    /**
     * 所属班级id
     */
    private String classId;

    /**
     * 语料组名称
     */
    private String title;

    /**
     * 语料组描述
     */
    private String description;

    /**
     * 语料组类型：1-测验；2-试卷；3-作业
     */
    private Integer type;

    /**
     * 难易程度：-1-未知；1～10难度递增
     */
    private Integer difficulty;

    /**
     * 公开类型：0-公开；1-私有
     */
    private Integer isPublic;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 截止时间
     */
    private Date endTime;

    /**
     * 创建者账号id
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * topic大题列表
     */
    private List<TopicVO> topics;


}
