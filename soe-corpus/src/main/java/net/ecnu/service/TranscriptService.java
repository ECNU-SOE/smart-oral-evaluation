package net.ecnu.service;

import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.model.TranscriptDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 成绩单 服务类
 * </p>
 *
 * @author LYW
 * @since 2023-03-28
 */
public interface TranscriptService extends IService<TranscriptDO> {

    Object save(TranscriptReq transcriptReq);

    Object getTranscript(TranscriptReq transcriptReq);
}
