package net.ecnu.service;

import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.model.TranscriptDO;
import com.baomidou.mybatisplus.extension.service.IService;
import net.ecnu.model.TranscriptDOExample;
import net.ecnu.model.common.PageData;
import net.ecnu.model.dto.req.TranscriptInfoReq;

import java.util.List;

/**
 * <p>
 * 成绩单 服务类
 * </p>
 *
 * @author LYW
 * @since 2023-03-28
 */
public interface TranscriptService extends IService<TranscriptDO> {

    long countByExample(TranscriptDOExample example);

    int deleteByExample(TranscriptDOExample example);


    int deleteByPrimaryKey(String id);


    int insert(TranscriptDO record);


    int insertSelective(TranscriptDO record);


    List<TranscriptDO> selectByExample(TranscriptDOExample example);


    TranscriptDO selectByPrimaryKey(String id);


    int updateByExampleSelective(TranscriptDO record,TranscriptDOExample example);


    int updateByExample(TranscriptDO record,TranscriptDOExample example);


    int updateByPrimaryKeySelective(TranscriptDO record);


    int updateByPrimaryKey(TranscriptDO record);


    int updateBatch(List<TranscriptDO> list);

    int batchInsert(List<TranscriptDO> list);

    Object save(TranscriptReq transcriptReq);

    Object getTranscript(TranscriptReq transcriptReq);

    PageData getTranscriptInfo(TranscriptInfoReq transcriptInfoReq);
}
