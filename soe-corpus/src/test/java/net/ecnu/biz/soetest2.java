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
import net.ecnu.mapper.TranscriptMapper;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.TranscriptDO;
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
    public TranscriptMapper transcriptMapper;
    @Test
    public void test() {
        TranscriptDO transcriptDO = transcriptMapper.selectById("transcript_1729890733864914944");
        System.out.println(transcriptDO);
    }
}
