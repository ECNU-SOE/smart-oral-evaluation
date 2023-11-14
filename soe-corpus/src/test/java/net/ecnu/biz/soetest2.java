package net.ecnu.biz;


import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.soe.v20180724.SoeClient;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitRequest;
import com.tencentcloudapi.soe.v20180724.models.TransmitOralProcessWithInitResponse;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.CorpusApplication;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.util.JsonData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CorpusApplication.class)
@Slf4j
public class soetest2 {

    @Autowired
    public CpsrcdMapper cpsrcdMapper;
    @Test
    public void test() {
        CpsrcdFilterReq cpsrcdFilterReq = new CpsrcdFilterReq();
        cpsrcdFilterReq.setType("朗读字词");
        cpsrcdFilterReq.setRefText("辞职");
        cpsrcdFilterReq.setDifficultyBegin(3);
        cpsrcdFilterReq.setDifficultyEnd(11);
        CpsrcdDO cpsrcdDO = cpsrcdMapper.getRand(cpsrcdFilterReq);
        System.out.println(cpsrcdDO);
    }
}
