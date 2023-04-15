package net.ecnu.db;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.UserApplication;
import net.ecnu.mapper.SysApiMapper;
import net.ecnu.mapper.SystemMapper;
import net.ecnu.model.authentication.SysApi;
import net.ecnu.model.authentication.SysApiNode;
import net.ecnu.model.authentication.tree.DataTreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @Author 刘少煜
 * @Date 2022/12/11 14:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class AuthTest {
    //$2a$10$jpvRrMYykpBv1.w9snhxC.1.cxoNu/93FO6JIIKSo3WI/oRFFDD8C
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysApiMapper sysApiMapper;

    @Resource
    private SystemMapper systemMapper;


    @Test
    public void passwordTest() {
        String password = "abcd1234";
        log.info("初始密码加密：{}", JSON.toJSON(passwordEncoder.encode(password)));
    }

    @Test
    public void apiIdGenerate() {
        List<Integer> list = new ArrayList<>();
        for (int i = 18; i <= 79; i++) {
            list.add(i);
        }
        String collect = list.stream().map(e -> String.valueOf(e)).collect(Collectors.joining(","));
        System.out.println("collect = " + collect);
    }

    //接口树形结构展示
    @Test
    public void testApiTree() {
        //查找level=1的API节点，即：根节点
        String apiNameLike = "";
        Boolean apiStatus = false;
        SysApi rootSysApi = sysApiMapper.selectOne(
                new QueryWrapper<SysApi>().eq("api_pid", 0));

        Long rootApiId = rootSysApi.getId();

        List<SysApi> sysApis = systemMapper.selectApiTree(rootApiId, apiNameLike, apiStatus);

        List<SysApiNode> sysApiNodes = sysApis.stream().map(item -> {
            SysApiNode bean = new SysApiNode();
            BeanUtils.copyProperties(item, bean);
            return bean;
        }).collect(Collectors.toList());

        if (StringUtils.isNotEmpty(apiNameLike) && apiStatus != null) {
            //return sysApiNodes;//根据api名称等查询会破坏树形结构，返回平面列表
            log.info("sysApiNodes pram:{}",JSON.toJSON(sysApiNodes));
        } else {//否则返回树型结构列表
            //return DataTreeUtil.buildTree(sysApiNodes, rootApiId);
            List<SysApiNode> sysApiNodesList = DataTreeUtil.buildTree(sysApiNodes, rootApiId);
            log.info("sysApiNodesList pram:{}",JSON.toJSON(sysApiNodesList));
        }
    }

}
