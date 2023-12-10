package net.ecnu.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.model.TranscriptDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.TranscriptDOExample;
import net.ecnu.model.dto.req.TranscriptInfoReq;
import net.ecnu.model.dto.resp.TranscriptInfoResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 成绩单 Mapper 接口
 * </p>
 *
 * @author LYW
 * @since 2022-11-19
 */
@Mapper
public interface TranscriptMapper extends BaseMapper<TranscriptDO> {

    long countByExample(TranscriptDOExample example);

    int deleteByExample(TranscriptDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(TranscriptDO record);

    int insertSelective(TranscriptDO record);

    List<TranscriptDO> selectByExample(TranscriptDOExample example);

    TranscriptDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TranscriptDO record, @Param("example") TranscriptDOExample example);

    int updateByExample(@Param("record") TranscriptDO record, @Param("example") TranscriptDOExample example);

    int updateByPrimaryKeySelective(TranscriptDO record);

    int updateByPrimaryKey(TranscriptDO record);

    int updateBatch(List<TranscriptDO> list);

    int batchInsert(@Param("list") List<TranscriptDO> list);

    IPage<TranscriptInfoResp> getTranscriptInfo(@Param("transcriptInfoReq") TranscriptInfoReq transcriptInfoReq, Page<TranscriptInfoResp> page);

    String getReviewerName(@Param("reviewerCode") String reviewerCode);
}
