package com.alpha.his.controller;

import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.WebUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alpha/his")
public class HISController {

    @PostMapping("/getUser")
    public ResponseMessage getUser(String hospitalCode, String idcard) {
        System.out.println("hospitalCode="+hospitalCode+",idcard="+idcard);
        return WebUtils.buildSuccessResponseMessage();
    }


}
