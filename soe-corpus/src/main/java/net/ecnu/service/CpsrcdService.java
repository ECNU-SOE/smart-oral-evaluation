package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.controller.request.CpsrcdReq;
import net.ecnu.model.CpsrcdDO;
import com.baomidou.mybatisplus.extension.service.IService;
import net.ecnu.model.vo.CpsrcdVO;
import org.springframework.web.multipart.MultipartFile;

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

    CpsrcdVO getCpsrcdDetail(String cpsrcdId);

    Object pageByFilter(CpsrcdFilterReq cpsrcdFilter, Page<CpsrcdDO> cpsrcdDOPage);
    Object random(CpsrcdFilterReq cpsrcdFilter);
}
