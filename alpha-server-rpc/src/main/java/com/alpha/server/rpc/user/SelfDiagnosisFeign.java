package com.alpha.server.rpc.user;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "alpha-self-diagnosis", fallbackFactory = SelfDiagnosisFallbackFactory.class)
public interface SelfDiagnosisFeign {

    @RequestMapping(value = "/main/test")
    public String getUserInfo();

}
