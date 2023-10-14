package net.ecnu.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.model.common.readWordByXF.ReadWord;

@Data
public class RecPaper {

    @JsonProperty("read_word")
    private ReadWord readWord;


}