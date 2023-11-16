package net.ecnu.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * user快照
 * </p>
 *
 * @author TGX
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TagVO implements Serializable {

    private Integer id;

    private String name;

    private Integer frequency;

    private Double weight;

    private Integer category;
}
