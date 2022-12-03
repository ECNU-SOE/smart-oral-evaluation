package net.ecnu.model.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ScoreDTO {

    @NotNull
    private Integer subTopic;

    @DecimalMin(value = "0", message = "proportion range 0～1")
    @DecimalMax(value = "1", message = "proportion range 0～1")
    @NotNull(message = "proportion can't be null")
    private Double proportion;

    @NotNull
    @Min(value = 1, message = "totalWords must greater than 1")
    private Integer totalWords;

    @NotNull
    private Integer wrongWords;

    @DecimalMin(value = "0", message = "pronCompletion range 0～1")
    @DecimalMax(value = "1", message = "pronCompletion range 0～1")
    @NotNull(message = "pronCompletion can't be null")
    private Double pronCompletion;

    @DecimalMin(value = "0", message = "pronAccuracy range 0～100")
    @DecimalMax(value = "100", message = "pronAccuracy range 0～100")
    @NotNull(message = "pronAccuracy can't be null")
    private Double pronAccuracy;

    @DecimalMin(value = "-1", message = "pronFluency range -1～1")
    @DecimalMax(value = "1", message = "pronFluency range -1～1")
    @NotNull(message = "pronFluency can't be null")
    private Double pronFluency;
}
