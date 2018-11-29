package com.alpha.server.rpc.his;

import com.alpha.commons.web.ResponseMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "alpha-his", fallbackFactory = HisApiFallbackFactory.class)
public interface HISApiFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/alpha/his/getUser")
    public ResponseMessage getUser(@RequestParam("hospitalCode") String hospitalCode, @RequestParam("idcard") String idcard);
}
