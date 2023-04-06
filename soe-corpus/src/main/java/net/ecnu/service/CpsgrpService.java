package net.ecnu.service;

import net.ecnu.controller.request.CpsgrpReq;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.model.CpsgrpDO;
import com.baomidou.mybatisplus.extension.service.IService;
import net.ecnu.model.common.PageData;

/**
 * <p>
 * 语料组(作业、试卷、测验) 服务类
 * </p>
 *
 * @author LYW
 * @since 2022-11-02
 */
public interface CpsgrpService extends IService<CpsgrpDO> {

    /**
     * 1、创建cpsgrp
     * 2、创建cpsrcd，语料的快照
     */
    Object create(CpsgrpReq cpsgrpReq);

    Object del(String cpsgrpId);

    Object detail(String cpsgrpId);

    Object genTranscript(TranscriptReq transcriptReq);

    Object pageByFilter(CpsgrpFilterReq cpsgrpFilter, PageData pageData);

    Object update(CpsgrpReq cpsgrpReq);
}
