package net.ecnu.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ScoreDTO {

    @NotNull
    private Integer subTopic;

    @NotNull
    private Double proportion;

    @NotNull
    private Integer totalWords;

    @NotNull
    private Integer wrongWords;

    @NotNull
    private Double pronCompletion;

    @NotNull
    private Double pronAccuracy;

    @NotNull
    private Double pronFluency;
}
