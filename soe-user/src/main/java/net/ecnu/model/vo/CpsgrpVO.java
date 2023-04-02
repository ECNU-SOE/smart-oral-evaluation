package net.ecnu.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * course快照
 * </p>
 *
 * @author TGX
 * @since 2023-3-8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CpsgrpVO implements Serializable {

    private String courseId;
    private String title;
    private String description;
    private Integer type;
    private Integer difficulty;
    private Integer isPublic;
    private Date startTime;
    private Date endTime;
    private String creator;


}
