package net.ecnu.model.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.ecnu.model.CpsrcdDO;

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
     * 语料组名称
     */
    private String name;

    /**
     * 语料组类型
     */
    private Integer type;

    /**
     * 语料组描述
     */
    private String description;

    /**
     * 开始时间
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
     * 题目列表
     */
    private List<CpsrcdVO> cpsrcdList;


}
