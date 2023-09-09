package net.ecnu.manager.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CorpusReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.CorpusManager;
import net.ecnu.mapper.CorpusMapper;
import net.ecnu.model.CorpusDO;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class CorpusManagerImpl implements CorpusManager {

    @Autowired
    private CorpusMapper corpusMapper;

    @Override
    public IPage<CorpusDO> pageByFilter(CorpusFilterReq corpusFilter, Page<CorpusDO> corpusDOPage) {
        QueryWrapper<CorpusDO> corpusDOQueryWrapper = new QueryWrapper<>();
        //语料id
        if (!StringUtils.isEmpty(corpusFilter.getCorpusId())) {
            corpusDOQueryWrapper.eq("id",corpusFilter.getCorpusId());
        }
        //测评模式
        if(!Objects.isNull(corpusFilter.getEvalMode())){
            corpusDOQueryWrapper.eq("eval_mode",corpusFilter.getEvalMode());
        }
        //难易程度
        if(!Objects.isNull(corpusFilter.getDifficulty())){
            corpusDOQueryWrapper.eq("difficulty",corpusFilter.getDifficulty());
        }
        //文本内容
        if(!StringUtils.isEmpty(corpusFilter.getTextValue())){
            corpusDOQueryWrapper.like("ref_text",corpusFilter.getTextValue());
        }
        corpusDOQueryWrapper.eq("del", 0);
        return corpusMapper.selectPage(corpusDOPage, corpusDOQueryWrapper);
    }

    @Override
    public void add(CorpusDO corpusDO) {
        if(corpusMapper.insert(corpusDO) < 0){
            log.info("添加语料异常，corpusDo入参:{}",JSON.toJSON(corpusDO));
            throw new BizException(BizCodeEnum.CORPUS_ADD_ERROR);
        }
    }

    @Override
    public void delCorpusInfo(String corpusId) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id",corpusId);
        updateWrapper.set("del",1);
        if(corpusMapper.update(null,updateWrapper) <= 0){
            log.info("语料删除异常，corpusId入参：{}", JSON.toJSON(corpusId));
            throw new BizException(BizCodeEnum.CORPUS_DEL_ERROR);
        }
    }

    @Override
    public void updateCorpusInfo(CorpusReq corpusReq) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        if (Objects.isNull(corpusReq.getCorpusId())) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        updateWrapper.eq("id", corpusReq.getCorpusId());
        updateWrapper.eq("del",0);
        CorpusDO corpusDO = new CorpusDO();
        BeanUtils.copyProperties(corpusReq,corpusDO);
        if (corpusMapper.update(corpusDO, updateWrapper) <= 0) {
            log.info("更新语料异常，corpusReq入参:{}", JSON.toJSON(corpusReq));
            throw new BizException(BizCodeEnum.CORPUS_UPDATE_ERROR);
        }
    }

}
