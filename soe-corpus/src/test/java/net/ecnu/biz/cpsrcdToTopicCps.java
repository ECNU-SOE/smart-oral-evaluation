package net.ecnu.biz;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.CorpusApplication;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.mapper.TopicCpsMapper;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.TopicCpsDO;
import net.ecnu.service.EvaluateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CorpusApplication.class)
@Slf4j
public class cpsrcdToTopicCps {

    @Autowired
    private CpsrcdMapper cpsrcdMapper;

    @Autowired
    private TopicCpsMapper topicCpsMapper;

    @Test
    public void test() {
        List<CpsrcdDO> cpsrcdDOS = cpsrcdMapper.selectList(new QueryWrapper<>());
        cpsrcdDOS.forEach(cpsrcdDO -> {
//            List<TopicCpsDO> topicCpsDOS = topicCpsMapper.selectList(new QueryWrapper<TopicCpsDO>()
//                    .eq("cpsrcd_id", cpsrcdDO.getId())
//                    .eq("topic_id", cpsrcdDO.getTopicId()));
            TopicCpsDO topicCpsDO = new TopicCpsDO();
//            if (topicCpsDOS != null) return;
//            topicCpsDO.setTopicId(cpsrcdDO.getTopicId());
            topicCpsDO.setCpsrcdId(cpsrcdDO.getId());
//            topicCpsDO.setCNum(cpsrcdDO.getCNum());
            topicCpsDO.setEnablePinyin(true);
            topicCpsDO.setScore(10.0);
            topicCpsMapper.insert(topicCpsDO);
        });
    }
}
