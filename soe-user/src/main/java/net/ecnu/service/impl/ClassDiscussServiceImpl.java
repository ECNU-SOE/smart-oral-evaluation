package net.ecnu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.enums.ClassRoleTypeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.ClassDiscussMapper;
import net.ecnu.mapper.ClassMapper;
import net.ecnu.mapper.UserMapper;
import net.ecnu.model.ClassDiscussDo;
import net.ecnu.model.DiscussAudioDo;
import net.ecnu.model.dto.DiscussDto;
import net.ecnu.model.dto.ForwardDto;
import net.ecnu.model.vo.DiscussVo;
import net.ecnu.model.vo.ReplyInfoVo;
import net.ecnu.service.ClassDiscussService;
import net.ecnu.service.DiscussAudioService;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 16:27
 */
@Slf4j
@Service
public class ClassDiscussServiceImpl implements ClassDiscussService {
    @Resource
    private ClassDiscussMapper classDiscussMapper;
    @Resource
    private ClassMapper classMapper;
    @Resource
    private DiscussAudioService discussAudioService;
    @Resource
    private UserMapper userMapper;

    public int deleteByPrimaryKey(Long discussId) {
        return classDiscussMapper.deleteByPrimaryKey(discussId);
    }


    public int insert(ClassDiscussDo record) {
        return classDiscussMapper.insert(record);
    }


    public int insertSelective(ClassDiscussDo record) {
        return classDiscussMapper.insertSelective(record);
    }


    public ClassDiscussDo selectByPrimaryKey(Long discussId) {
        return classDiscussMapper.selectByPrimaryKey(discussId);
    }


    public int updateByPrimaryKeySelective(ClassDiscussDo record) {
        return classDiscussMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(ClassDiscussDo record) {
        return classDiscussMapper.updateByPrimaryKey(record);
    }

    @Override
    public int addLikes(String discussId) {
        return classDiscussMapper.addLikes(discussId);
    }

    @Override
    public Boolean addDiscuss(DiscussDto discussDto) {
        try {
            String currentAccountNo = RequestParamUtil.currentAccountNo();
            ClassDiscussDo record = new ClassDiscussDo();
            //话题标题
            record.setDiscussTitle(StringUtils.isEmpty(discussDto.getDiscussTitle()) ? "" : discussDto.getDiscussTitle());
            //话题内容
            if (!StringUtils.isEmpty(discussDto.getDiscussTest())) {
                record.setDiscussContent(discussDto.getDiscussTest());
            } else {
                log.info("添加话题内容异常：话题内容不能为空，入参discussDto：{}", JSON.toJSON(discussDto));
                throw new BizException(BizCodeEnum.DISCUSS_AUDIO_ADD_ERROR.getCode(), "话题内容不能为空");
            }
            //该话题插入后，即为根节点
            record.setIsLeaf(true);
            //没有父节点,默认为父节点为0
            record.setParentId(0L);
            record.setClassId(discussDto.getClassId());
            //非转发的帖子，默认为0
            record.setForwardId(0L);
            record.setLikeCount(0);
            record.setReplyNumber(0);
            //发布人id
            record.setPublisher(currentAccountNo);
            //新增话题同时获取对应的主键id
            if (classDiscussMapper.insertSelective(record) <= 0) {
                throw new BizException(BizCodeEnum.DISCUSS_AUDIO_ADD_ERROR);
            }
            //插入话题对应音频
            if (!CollectionUtils.isEmpty(discussDto.getAudioUrl())) {
                List<DiscussAudioDo> audioList = new ArrayList<>();
                for (String url : discussDto.getAudioUrl()) {
                    DiscussAudioDo discussAudioDo = new DiscussAudioDo();
                    discussAudioDo.setAudioUrl(url);
                    discussAudioDo.setDiscussId(record.getDiscussId());
                    discussAudioDo.setDelFlg(false);
                    discussAudioDo.setUploadTime(new Date());
                    audioList.add(discussAudioDo);
                }
                if (!discussAudioService.saveBatch(audioList)) {
                    throw new BizException(BizCodeEnum.DISCUSS_AUDIO_ADD_ERROR);
                }
            }
            return true;
        } catch (Exception e) {
            log.info("新增话题异常，异常信息：{}", JSON.toJSON(e.getMessage()));
            //手动强制回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Override
    public Page<DiscussVo> getDiscussInfo(String classId, Integer pageNum, Integer pageSize) {
        //获取该班级下的所有话题（不包含回复）
        LambdaQueryWrapper<ClassDiscussDo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ClassDiscussDo::getClassId, classId)
                .eq(ClassDiscussDo::getParentId, 0).eq(ClassDiscussDo::getDelFlg, 0).orderByDesc(ClassDiscussDo::getSortTime);
        Page<ClassDiscussDo> courseDiscussDoPage = classDiscussMapper.selectPage(new Page<>(pageNum, pageSize), lambdaQueryWrapper);
        Page<DiscussVo> resultPage = new Page<>();
        BeanUtils.copyProperties(courseDiscussDoPage, resultPage);
        List<DiscussVo> discussVoList = new ArrayList<>();
        //检查是否是转发的帖子
        for (ClassDiscussDo record : courseDiscussDoPage.getRecords()) {
            DiscussVo discussVo = new DiscussVo();
            BeanUtils.copyProperties(record, discussVo);
            //该话题为转发贴
            if (record.getForwardId() != 0L) {
                //获取转发贴数据
                ClassDiscussDo classDiscussDo = classDiscussMapper.selectByPrimaryKey(record.getForwardId());
                //查询该贴的转发班级次数
                DiscussVo forwardVo = new DiscussVo();
                BeanUtils.copyProperties(classDiscussDo, forwardVo);
                //获取转发贴的发布人姓名以及班级身份
                String publisherName = getPublisherInfo(forwardVo.getPublisher());
                forwardVo.setPublisherName(publisherName);
                discussVo.setForwardDiscuss(forwardVo);
            }
            //获取话题的发布人姓名以及班级身份
            String publisherName = getPublisherInfo(record.getPublisher());
            //转发班级次数
            Integer publishClassesNum = classDiscussMapper.selectPublishClassesNum(record.getDiscussId());
            discussVo.setPublisherName(publisherName);
            discussVo.setPublishClassesNum(publishClassesNum);
            //获取该话题的上传音频
            List<String> audioUrlList = discussAudioService.selectByDiscussId(record.getDiscussId());
            if (!CollectionUtils.isEmpty(audioUrlList)) {
                discussVo.setAudioList(audioUrlList);
            }
            //获取回复数
            discussVo.setReplyNumber(record.getReplyNumber() > 999 ? 999 : record.getReplyNumber());
            discussVoList.add(discussVo);
        }
        resultPage.setRecords(discussVoList);
        return resultPage;
    }

    /**
     * 获取话题的发布人姓名以及班级身份方法
     * @param publisher 发布人id（user.account_no）
     * **/
    public String getPublisherInfo(String publisher){
        Map<String,Object> publisherInfoMap = classDiscussMapper.getPublisherInfo(publisher);
        String publisherName;
        if (!CollectionUtils.isEmpty(publisherInfoMap) && publisherInfoMap.size() > 0) {
            Object roleType = publisherInfoMap.get("roleType");
            publisherName = String.valueOf(publisherInfoMap.get("userName")) + "(" + ClassRoleTypeEnum.getMsgByCode(Integer.valueOf(roleType.toString())) + ")";
        } else {
            publisherName = "用户" + publisher;
        }
        return publisherName;
    }

    @Override
    public Page<ReplyInfoVo> getReplyInfo(String discussId, Integer pageNum, Integer pageSize) {
        //获取以discussId为父节点的讨论记录
        LambdaQueryWrapper<ClassDiscussDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClassDiscussDo::getParentId,discussId).eq(ClassDiscussDo::getDelFlg,0)
                .orderByDesc(ClassDiscussDo::getReleaseTime);
        Page<ClassDiscussDo> courseDiscussDoPage = classDiscussMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<ReplyInfoVo> replyInfoPage = new Page<>();
        BeanUtils.copyProperties(courseDiscussDoPage,replyInfoPage);
        List<ReplyInfoVo> replyInfoVoList = new ArrayList<>();
        for (ClassDiscussDo record : courseDiscussDoPage.getRecords()) {
            ReplyInfoVo replyInfoVo = new ReplyInfoVo();
            BeanUtils.copyProperties(record,replyInfoVo);
            //回复数
            replyInfoVo.setReplyNumber(record.getReplyNumber() > 999 ? 999 : record.getReplyNumber());
            //获得该回复上传的音频
            List<String> audioUrlList = discussAudioService.selectByDiscussId(record.getDiscussId());
            if (!CollectionUtils.isEmpty(audioUrlList)) {
                replyInfoVo.setAudioList(audioUrlList);
            }
            replyInfoVoList.add(replyInfoVo);
        }
        replyInfoPage.setRecords(replyInfoVoList);
        return replyInfoPage;
    }

    @Override
    public void reply(DiscussDto discussDto) {
        try {
            String currentAccountNo = RequestParamUtil.currentAccountNo();
            //校验父节点是否存在
            ClassDiscussDo parentDiscuss = classDiscussMapper.selectByPrimaryKey(discussDto.getParentId());
            if (Objects.nonNull(parentDiscuss)) {
                //判断是否为叶节点
                if (parentDiscuss.getIsLeaf()) {
                    parentDiscuss.setIsLeaf(false);
                }
                parentDiscuss.setReplyNumber(parentDiscuss.getReplyNumber() + 1);
            } else {
                throw new BizException(BizCodeEnum.DISCUSS_REPLY_ADD_ERROR);
            }
            ClassDiscussDo classDiscussDo = new ClassDiscussDo();
            classDiscussDo.setIsLeaf(true);
            classDiscussDo.setClassId(discussDto.getClassId());
            classDiscussDo.setParentId(parentDiscuss.getDiscussId());
            classDiscussDo.setPublisher(currentAccountNo);
            classDiscussDo.setDiscussContent(discussDto.getDiscussTest());
            classDiscussDo.setForwardId(0L);
            classDiscussDo.setReplyNumber(0);
            classDiscussDo.setLikeCount(0);
            classDiscussDo.setDelFlg(false);
            //新增回复记录，并回传对应的主键id
            if(classDiscussMapper.insertSelective(classDiscussDo) <= 0){
                throw new BizException(BizCodeEnum.DISCUSS_REPLY_ADD_ERROR);
            }
            //新增对应记录的音频
            List<DiscussAudioDo> audioDoList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(discussDto.getAudioUrl())){
                for (String audioUrl : discussDto.getAudioUrl()) {
                    DiscussAudioDo discussAudioDo = new DiscussAudioDo();
                    discussAudioDo.setDiscussId(classDiscussDo.getDiscussId());
                    discussAudioDo.setAudioUrl(audioUrl);
                    discussAudioDo.setUploadTime(new Date());
                    audioDoList.add(discussAudioDo);
                }
                //批量新增音频数据
                discussAudioService.saveBatch(audioDoList);
            }
            //更新父节点
            classDiscussMapper.updateByPrimaryKeySelective(parentDiscuss);
        } catch (Exception e) {
            log.info("添加回复异常，异常信息：{}", JSON.toJSON(e.getMessage()));
            //手动强制回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public int forward(ForwardDto forwardDto) {
        log.info("转发话题入参:{}",JSON.toJSON(forwardDto));
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        //创建一条转发记录
        ClassDiscussDo record = new ClassDiscussDo();
        //转发评论
        record.setDiscussContent(forwardDto.getDiscussTest());
        //转发到哪个班级
        record.setClassId(forwardDto.getClassId());
        //转发的帖子只能是根节点
        record.setIsLeaf(true);
        //转发的帖子的源discussId
        record.setForwardId(forwardDto.getForwardId());
        //点赞数
        record.setLikeCount(0);
        //回复数
        record.setReplyNumber(0);
        record.setPublisher(currentAccountNo);
        //没有父节点,默认父节点为0
        record.setParentId(0L);
        //新增话题同时获取对应的主键id
        Integer discussId = classDiscussMapper.insertSelective(record);
        return discussId;
    }

    @Override
    public int topDiscuss(Long discussId) {
        ClassDiscussDo classDiscussDo = new ClassDiscussDo();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2100,1,1,1,0);
        Date sortTime = calendar.getTime();
        classDiscussDo.setDiscussId(discussId);
        classDiscussDo.setSortTime(sortTime);
        return classDiscussMapper.updateByPrimaryKeySelective(classDiscussDo);
    }

}
