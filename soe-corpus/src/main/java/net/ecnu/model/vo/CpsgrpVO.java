package net.ecnu.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
     * 难易程度：[A~J][0~9]
     */
    private String difficulty;

    /**
     * 公开类型：0-公开；1-私有
     */
    private Integer isPrivate;

    /**
     * 修改状态：0允许修改、1允许创建者修改、2不允许修改
     */
    private Integer modStatus;

    /**
     * 起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 创建者账号id
     */
    private String creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * topic大题列表
     */
    private List<TopicVO> topics;


}
