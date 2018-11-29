package com.alpha.hystric;

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.domain.Message;
import com.alpha.fegin.SchedualServiceDataAdvance;
import org.springframework.stereotype.Service;

/**
 * Created by MR.Wu on 2017-08-18.
 */
@Service
public class SchedualServiceDataAdvanceHystric implements SchedualServiceDataAdvance {

    @Override
    public Message invokeDataAdvanceService(HisRegisterYygh hisRegisterYygh) {
        Message message = new Message();
        message.setMessage("访问服务出现异常，请重试");
        return message;
    }
}
