package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.model.dto.ScoreDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TranscriptReq {

    /**
     * 语料组ID
     */
    @NotEmpty(message = "cpsgrpId cannot be empty", groups = {Create.class})
    private String cpsgrpId;

    /**
     * 机器评测得分
     */
    @NotNull(message = "suggestedScore cannot be empty", groups = {Create.class})
    private Double suggestedScore;

    /**
     * 报告json
     */
    @NotEmpty(message = "resJson cannot be empty", groups = {Create.class})
    private String resJson;

}
