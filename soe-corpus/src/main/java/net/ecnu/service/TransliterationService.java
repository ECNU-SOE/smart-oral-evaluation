package net.ecnu.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import net.ecnu.model.Transliteration;
import net.ecnu.model.TransliterationExample;
import net.ecnu.mapper.TransliterationMapper;

public interface TransliterationService {


    long countByExample(TransliterationExample example);

    int deleteByExample(TransliterationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Transliteration record);

    int insertSelective(Transliteration record);

    List<Transliteration> selectByExample(TransliterationExample example);

    Transliteration selectByPrimaryKey(Long id);

    int updateByExampleSelective(Transliteration record, TransliterationExample example);

    int updateByExample(Transliteration record, TransliterationExample example);

    int updateByPrimaryKeySelective(Transliteration record);

    int updateByPrimaryKey(Transliteration record);
}

