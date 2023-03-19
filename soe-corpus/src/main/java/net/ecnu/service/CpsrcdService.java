package net.ecnu.service;

import net.ecnu.controller.request.CpsrcdReq;
import net.ecnu.model.CpsrcdDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * Cpsrcd快照
 * </p>
 *
 * @author LYW
 * @since 2023-03-17
 */
public interface CpsrcdService extends IService<CpsrcdDO> {

    Object mod(CpsrcdReq cpsrcdReq);

    /**
     * 向指定语料组cpsgrp中添加子题cpsrcd
     */
    Object add(CpsrcdReq cpsrcdReq);

    Object del(String cpsrcdId);
}
