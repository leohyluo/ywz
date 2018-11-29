package com.alpha.wechar.controller;

import com.alpha.commons.api.tencent.offical.dto.AccessTokenDTO;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.WebUtils;
import com.alpha.wechar.service.OfficalAccountService;
import com.alpha.wechar.utils.WeixinSignUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("weixin")
public class WeixinController {

    @Resource
    private OfficalAccountService officalAccountService;

    /**
     *
     * @param url
     * @param flushFlag
     * @return
     * @throws Exception
     */
    @GetMapping("/scan")
    public ResponseMessage scan(String url, int flushFlag) throws Exception {
        AccessTokenDTO accessToken = officalAccountService.getAccessToken(flushFlag);
        Map<String, Object> map = WeixinSignUtil.sign(accessToken, url);
        Object errCode = map.get("errcode");
        if (null != errCode) {
            return WebUtils.buildResponseMessage(Integer.valueOf(errCode.toString()), map.get("errmsg").toString());
        }
        return WebUtils.buildSuccessResponseMessage(map);
    }

    @GetMapping("/test")
    public ResponseMessage test() {
        return WebUtils.buildSuccessResponseMessage();
    }
}
