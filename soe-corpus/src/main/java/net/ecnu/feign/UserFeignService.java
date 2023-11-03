package net.ecnu.feign;

import com.alibaba.fastjson.JSONObject;
import net.ecnu.feign.req.UserFilterReq;
import net.ecnu.util.JsonData;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "soe-user-server")
public interface UserFeignService {

    /**
     * 使用流量包
     */
//    @PostMapping(value = "/api/user/v1/reduce", headers = {"rpc-token=${rpc.token}"})
//    JsonData getUsers(@RequestBody UserFilterReq request);

    @GetMapping(value = "/api/cour/v1/detail",headers = {"token=soe-token-eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbWFydC1vcmFsLWV2YWx1YXRpb24iLCJsb2dpblVzZXIiOnsiYWNjb3VudE5vIjoidXNlcl8xNTg3NDIyOTk5MDQzMjQ4MTI4IiwiaWRlbnRpZnlJZCI6IjUxMjU1OTAyMDciLCJyb2xlSWQiOm51bGwsIm5pY2tOYW1lIjoi5a6M576O55qE5rKJ552hIiwicmVhbE5hbWUiOiLllJDlm73lhbQiLCJmaXJzdExhbmd1YWdlIjpudWxsLCJwaG9uZSI6IjE4Nzg2OTc4MjcyIiwibWFpbCI6IjE0MzMzODE1MzRAcXEuY29tIn0sImlhdCI6MTY5ODgwNDA0NCwiZXhwIjoxNjk5NDA4ODQ0fQ.Am2ETfnzuZXNo3eelm9M03Isz1PLM0HTApApdRkQTwY"})
    JsonData getCourse(@RequestParam("id")String id);
}
