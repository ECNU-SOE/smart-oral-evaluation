package net.ecnu.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
public class SignVO implements Serializable {

    private String userId;

    private Integer totalDays;

    private Integer continueDays;

    private LocalDate lastSign;

    private Integer resignNum;

    private List<LocalDate> signDates;

}
