package com.alpha.fegin;

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.domain.Message;
import com.alpha.hystric.SchedualServiceDataAdvanceHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by MR.Wu on 2017-08-18.
 * 定义一个feign接口，来指定调用哪一个服务
 * 当前做为数据预处理消费者，则直接调用数据推送的提供者服务 alpha-push-msg
 */
@FeignClient(value = "alpha-push-msg", fallback = SchedualServiceDataAdvanceHystric.class)
public interface SchedualServiceDataAdvance {

    /**
     * 调用服务的哪个方法,开始分析方法
     *
     * @return
     */
    @RequestMapping(value = "data/push", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    Message invokeDataAdvanceService(@RequestBody HisRegisterYygh hisRegisterYygh);

}
