package net.ecnu.feign;

import net.ecnu.feign.req.UserFilterReq;
import net.ecnu.util.JsonData;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "soe-user-server",url = "http://47.101.58.72:8888/user-server")
public interface UserFeignService {
    @PostMapping("/api/user/v1/list")
    JsonData getUsers(@RequestParam(value = "cur", defaultValue = "1") int cur,
                      @RequestParam(value = "size", defaultValue = "50") int size,
                      @RequestBody UserFilterReq userFilterReq,
                      @RequestHeader("token") String token);

    @GetMapping("/api/user/v1/name")
    JsonData getName(@RequestParam("accountNo") String accountNo,@RequestHeader("token")String token);
}
