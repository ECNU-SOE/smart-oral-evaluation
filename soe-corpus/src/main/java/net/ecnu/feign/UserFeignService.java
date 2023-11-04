package net.ecnu.feign;

import net.ecnu.feign.req.UserFilterReq;
import net.ecnu.util.JsonData;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "soe-user-server",url = "http://47.101.58.72:8888/user-server")
public interface UserFeignService {

    /**
     * 使用流量包
     */
//    @PostMapping(value = "/api/user/v1/reduce", headers = {"rpc-token=${rpc.token}"})
//    JsonData getUsers(@RequestBody UserFilterReq request);

//    @GetMapping(value = "/api/cour/v1/detail")
//    JsonData getCourse(@RequestParam("id")String id, @RequestHeader("token")String token);

    @PostMapping("/api/user/v1/list")
    JsonData getUsers(@RequestParam(value = "cur", defaultValue = "1") int cur,
                      @RequestParam(value = "size", defaultValue = "50") int size,
                      @RequestBody UserFilterReq userFilterReq,
                      @RequestHeader("token") String token);
}
