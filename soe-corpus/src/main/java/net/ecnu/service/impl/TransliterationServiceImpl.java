package net.ecnu.service.impl;

import net.ecnu.mapper.TransliterationMapper;
import net.ecnu.model.Transliteration;
import net.ecnu.model.TransliterationExample;
import net.ecnu.service.TranscriptService;
import net.ecnu.service.TransliterationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class TransliterationServiceImpl implements TransliterationService {

    @Resource
    private TransliterationMapper transliterationMapper;


    public long countByExample(TransliterationExample example) {
        return transliterationMapper.countByExample(example);
    }


    public int deleteByExample(TransliterationExample example) {
        return transliterationMapper.deleteByExample(example);
    }


    public int deleteByPrimaryKey(Long id) {
        return transliterationMapper.deleteByPrimaryKey(id);
    }


    public int insert(Transliteration record) {
        return transliterationMapper.insert(record);
    }


    public int insertSelective(Transliteration record) {
        return transliterationMapper.insertSelective(record);
    }


    public List<Transliteration> selectByExample(TransliterationExample example) {
        return transliterationMapper.selectByExample(example);
    }


    public Transliteration selectByPrimaryKey(Long id) {
        return transliterationMapper.selectByPrimaryKey(id);
    }


    public int updateByExampleSelective(Transliteration record,TransliterationExample example) {
        return transliterationMapper.updateByExampleSelective(record,example);
    }


    public int updateByExample(Transliteration record,TransliterationExample example) {
        return transliterationMapper.updateByExample(record,example);
    }


    public int updateByPrimaryKeySelective(Transliteration record) {
        return transliterationMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(Transliteration record) {
        return transliterationMapper.updateByPrimaryKey(record);
    }

}
