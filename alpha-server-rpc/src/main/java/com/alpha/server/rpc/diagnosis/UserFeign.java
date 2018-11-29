package com.alpha.server.rpc.diagnosis;

import com.alpha.server.rpc.user.SelfDiagnosisFallbackFactory;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "alpha-user", fallbackFactory = SelfDiagnosisFallbackFactory.class)
public interface UserFeign {

    @RequestMapping(value = "/user/auth")
    public String Authorization();

    @RequestMapping(value = "/user/get")
    public UserInfo getUserInfo();

}
