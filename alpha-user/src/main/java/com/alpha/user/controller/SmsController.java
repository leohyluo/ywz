package com.alpha.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.commons.sms.SMSMessageComponent;
import com.alpha.commons.util.RandomUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.WebUtils;

@RestController
@RequestMapping("/sms")
public class SmsController {

	/**
	 * 发送短信
	 * @param mobile
	 * @return
	 */
	@PostMapping("/send")
	public ResponseMessage send(String mobile) {
		String smsCode = RandomUtils.generateString(6);
		String smsText = "【智能导诊】您的验证码:"+smsCode;
		SMSMessageComponent smsMessageComponent = SMSMessageComponent.getInstance();
		int status = smsMessageComponent.sendMessage(mobile, smsText);//发短信
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		map.put("smsCode", smsCode);
		map.put("smsText", smsText);
		return WebUtils.buildSuccessResponseMessage(map);
	}
}
