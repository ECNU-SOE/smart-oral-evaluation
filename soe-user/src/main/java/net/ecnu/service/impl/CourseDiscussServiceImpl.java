package net.ecnu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.ClassMapper;
import net.ecnu.mapper.CourseDiscussMapper;
import net.ecnu.mapper.UserMapper;
import net.ecnu.model.CourseDiscussDo;
import net.ecnu.model.DiscussAudioDo;
import net.ecnu.model.UserDO;
import net.ecnu.model.dto.DiscussDto;
import net.ecnu.service.CourseDiscussService;
import net.ecnu.service.DiscussAudioService;
import net.ecnu.util.RequestParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 16:27
 */
@Slf4j
@Service
public class CourseDiscussServiceImpl implements CourseDiscussService {
    @Resource
    private CourseDiscussMapper courseDiscussMapper;
    @Resource
    private ClassMapper classMapper;
    @Resource
    private DiscussAudioService discussAudioService;
    @Resource
    private UserMapper userMapper;

    public int deleteByPrimaryKey(Long discussId) {
        return courseDiscussMapper.deleteByPrimaryKey(discussId);
    }


    public int insert(CourseDiscussDo record) {
        return courseDiscussMapper.insert(record);
    }


    public int insertSelective(CourseDiscussDo record) {
        return courseDiscussMapper.insertSelective(record);
    }


    public CourseDiscussDo selectByPrimaryKey(Long discussId) {
        return courseDiscussMapper.selectByPrimaryKey(discussId);
    }


    public int updateByPrimaryKeySelective(CourseDiscussDo record) {
        return courseDiscussMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(CourseDiscussDo record) {
        return courseDiscussMapper.updateByPrimaryKey(record);
    }

    @Override
    public int addLikes(String discussId) {
        return courseDiscussMapper.addLikes(discussId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addDiscuss(DiscussDto discussDto) {
        try {
            CourseDiscussDo record = new CourseDiscussDo();
            record.setDiscussContent(discussDto.getDiscussTest());
            //该话题插入后，即为根节点
            record.setIsLeaf(true);
            //没有父节点,默认为父节点为0
            record.setParentId(0L);
            record.setClassId(discussDto.getClassId());
            //新增话题同时获取对应的主键id
            Integer discussId = courseDiscussMapper.insertSelective(record);
            //插入话题对应音频
            List<DiscussAudioDo> audioList = new ArrayList<>();
            for (String url : discussDto.getAudioUrl()) {
                DiscussAudioDo discussAudioDo = new DiscussAudioDo();
                discussAudioDo.setAudioUrl(url);
                discussAudioDo.setDiscussId(discussId.longValue());
                discussAudioDo.setDelFlg(false);
                audioList.add(discussAudioDo);
            }
            if(!discussAudioService.saveBatch(audioList)){
                throw new BizException(BizCodeEnum.DISCUSS_AUDIO_ADD_ERROR);
            }
            return true;
        }catch (Exception e){
            log.info("新增话题异常，异常信息：{}", JSON.toJSON(e.getMessage()));
            //手动强制回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Override
    public Page<CourseDiscussDo> getDiscussInfo(String classId, Integer pageNum, Integer pageSize) {
        //获取该课程下的所有话题（不包含回复）
        LambdaQueryWrapper<CourseDiscussDo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CourseDiscussDo::getClassId,classId)
                .eq(CourseDiscussDo::getParentId,0).eq(CourseDiscussDo::getDelFlg,0);
        Page<CourseDiscussDo> courseDiscussDoPage = courseDiscussMapper.selectPage(new Page<>(pageNum, pageSize), lambdaQueryWrapper);
        return courseDiscussDoPage;
    }

    @Override
    public Page<CourseDiscussDo> getReplyInfo(String discussId, Integer pageNum, Integer pageSize) {
        //获取以discussId为父节点的讨论记录
        LambdaQueryWrapper<CourseDiscussDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseDiscussDo::getParentId,discussId).eq(CourseDiscussDo::getDelFlg,0);
        Page<CourseDiscussDo> courseDiscussDoPage = courseDiscussMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return courseDiscussDoPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reply(DiscussDto discussDto) {
        try {
            String currentAccountNo = RequestParamUtil.currentAccountNo();
            //校验父节点是否存在
            CourseDiscussDo parentDiscuss = courseDiscussMapper.selectByPrimaryKey(discussDto.getParentId());
            //获取用户相关信息
            LambdaQueryWrapper<UserDO> userWrapper = new LambdaQueryWrapper<>();
            userWrapper.eq(UserDO::getAccountNo,currentAccountNo).eq(UserDO::getDel,0);
            UserDO userDO = userMapper.selectOne(userWrapper);
            if(Objects.nonNull(parentDiscuss)){
                //判断是否为根节点
                if(parentDiscuss.getIsLeaf()){
                    parentDiscuss.setIsLeaf(false);
                }
            }else{
                throw new BizException(BizCodeEnum.DISCUSS_REPLY_ADD_ERROR);
            }
            CourseDiscussDo courseDiscussDo = new CourseDiscussDo();
            courseDiscussDo.setIsLeaf(true);
            courseDiscussDo.setClassId(discussDto.getClassId());
            courseDiscussDo.setParentId(parentDiscuss.getDiscussId());
            courseDiscussDo.setPublisher(userDO.getNickName());
            courseDiscussDo.setDiscussContent(discussDto.getDiscussTest());
            //新增回复记录，并回传对应的主键id
            Integer discussId = courseDiscussMapper.insertSelective(courseDiscussDo);
            //新增对应记录的音频
            List<DiscussAudioDo> audioDoList = new ArrayList<>();
            for (String audioUrl : discussDto.getAudioUrl()) {
                DiscussAudioDo discussAudioDo = new DiscussAudioDo();
                discussAudioDo.setDiscussId(discussId.longValue());
                discussAudioDo.setAudioUrl(audioUrl);
                audioDoList.add(discussAudioDo);
            }
            //批量新增音频数据
            discussAudioService.saveBatch(audioDoList);
            //更新父节点
            courseDiscussMapper.updateByPrimaryKeySelective(parentDiscuss);
        }catch (Exception e){
            log.info("添加回复异常，异常信息：{}", JSON.toJSON(e.getMessage()));
            //手动强制回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
