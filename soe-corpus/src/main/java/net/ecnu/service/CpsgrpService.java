package net.ecnu.service;

import net.ecnu.controller.request.CpsgrpCreateReq;
import net.ecnu.model.CpsgrpDO;
import com.baomidou.mybatisplus.extension.service.IService;

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
     *  1、创建cpsgrp
     *  2、创建cpsrcd，语料的快照
     */
    Object create(CpsgrpCreateReq cpsgrpCreateReq);

    Object del(String cpsgrpId);

    Object detail(String cpsgrpId);
}
