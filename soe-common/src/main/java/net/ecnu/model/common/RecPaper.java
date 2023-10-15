package net.ecnu.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.model.common.readChapterByXF.ReadChapter;
import net.ecnu.model.common.readSentenceByXF.ReadSentence;
import net.ecnu.model.common.readSyllableByXF.ReadSyllable;
import net.ecnu.model.common.readWordByXF.ReadWord;

/**
 * 各大评测模式的出参类
 * **/
@Data
public class RecPaper {

    @JsonProperty("read_syllable")
    private ReadSyllable readSyllable;

    @JsonProperty("read_word")
    private ReadWord readWord;

    @JsonProperty("read_sentence")
    private ReadSentence readSentence;

    @JsonProperty("read_chapter")
    private ReadChapter readChapter;
}