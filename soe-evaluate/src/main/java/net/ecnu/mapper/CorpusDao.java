package net.ecnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.domain.Corpus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
public interface CorpusDao extends BaseMapper<Corpus> {
}
