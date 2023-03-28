package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.model.dto.ScoreDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TranscriptReq {

    @NotEmpty
    private String cpsgrpId;

    @NotNull
    private Double suggestedScore;

    @NotEmpty
    private String resJson;

}
