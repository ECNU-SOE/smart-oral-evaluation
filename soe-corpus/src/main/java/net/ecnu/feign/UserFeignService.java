package net.ecnu.feign;

import net.ecnu.feign.req.UserFilterReq;
import net.ecnu.util.JsonData;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "soe-user-server")
public interface UserFeignService {

    /**
     * 使用流量包
     */
    @PostMapping(value = "/api/user/v1/reduce", headers = {"rpc-token=${rpc.token}"})
    JsonData getUsers(@RequestBody UserFilterReq request);

}
