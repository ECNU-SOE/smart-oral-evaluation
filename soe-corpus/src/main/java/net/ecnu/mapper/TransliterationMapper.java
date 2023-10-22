package net.ecnu.mapper;

import java.util.List;

import net.ecnu.model.Transliteration;
import net.ecnu.model.TransliterationExample;
import org.apache.ibatis.annotations.Param;

public interface TransliterationMapper {
    long countByExample(TransliterationExample example);

    int deleteByExample(TransliterationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Transliteration record);

    int insertSelective(Transliteration record);

    List<Transliteration> selectByExample(TransliterationExample example);

    Transliteration selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Transliteration record, @Param("example") TransliterationExample example);

    int updateByExample(@Param("record") Transliteration record, @Param("example") TransliterationExample example);

    int updateByPrimaryKeySelective(Transliteration record);

    int updateByPrimaryKey(Transliteration record);

    int updateBatch(List<Transliteration> list);

    int batchInsert(@Param("list") List<Transliteration> list);
}