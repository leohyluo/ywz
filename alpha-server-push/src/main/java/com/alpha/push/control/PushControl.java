package com.alpha.push.control;

import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.push.service.PushMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by MR.Wu on 2018-09-03.
 */
@RestController
@RequestMapping("data")
public class PushControl {

    @Autowired
    private PushMessageService pushMessageService;
    @Resource
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    @RequestMapping(value = "push", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void dataAdvance(@RequestBody(required = true) HisRegisterYygh hisRegisterYygh) {
        pushMessageService.pushMessage(hisRegisterYygh);
    }

    @GetMapping("/send/{id}")
    public ResponseMessage sendByWeb(@PathVariable Long id) {
        HisRegisterYygh hisRegisterYygh = hisRegisterYyghMapper.selectByPrimaryKey(id);
        if (hisRegisterYygh == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.REGISTER_INFO_NOTFOUND);
        }
        pushMessageService.pushMessage(hisRegisterYygh);
        return WebUtils.buildSuccessResponseMessage();
    }
}
