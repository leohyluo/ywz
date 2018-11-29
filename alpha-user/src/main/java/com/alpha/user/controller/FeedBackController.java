package com.alpha.user.controller;

import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.System;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserFeedback;
import com.alpha.user.pojo.UserFeedBackItem;
import com.alpha.user.pojo.vo.UserFeedBackItemVo;
import com.alpha.user.service.UserBasicRecordService;
import com.alpha.user.service.UserFeedbackService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xc.xiong on 2017/10/12.
 */
@RestController
@RequestMapping("/feedback")
public class FeedBackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedBackController.class);

    @Resource
    private UserFeedbackService userFeedbackService;
    @Resource
    private UserBasicRecordService userBasicRecordService;

    @Value("${hospital.code}")
    private String hospitalCode;

    /**
     * 接收用户反馈信息
     *
     * @param userFeedback
     * @return
     */
    @PostMapping("/put")
    public ResponseMessage authorization(UserFeedback userFeedback) {
    	LOGGER.info("接收用户反馈信息>>{}", userFeedback);
    	/*if (userFeedback != null && StringUtils.isNotEmpty(userFeedback.getContent()) && userFeedback.getUserId() != null) {
                userFeedbackService.saveUserFeedback(userFeedback);
            }*/
    	
    	Map<String, String> resultMap = userFeedbackService.saveUserFeedback(userFeedback);
        return WebUtils.buildSuccessResponseMessage(resultMap);
    }

    /**
     * 查看点赞次数及用户是否已经点过赞
	 * type:
	 * ALPHA("alpha", "阿尔法医生"),
	 * WOMAN("woman", "妇科"),
	 * PRE("pre", "预问诊");
     * @return
     */
    @PostMapping("/useful/query/{diagnosisId}/{type}")
    public ResponseMessage queryUseful(@PathVariable Long diagnosisId, @PathVariable String type) {
    	String clicked = "0";
    	String useful = "0";
    	//查询用户是否已经点过赞
    	List<UserFeedback> feedbackList = userFeedbackService.listUserFeedback(diagnosisId);
    	if(CollectionUtils.isNotEmpty(feedbackList)) {
    		UserFeedback feedback = feedbackList.get(0);
    		if(feedback.getUseful() != null && feedback.getUseful() == 1) {
    			clicked = "1";
    		}
    	}
    	//查看点赞次数
    	SysConfig sysConfig = userFeedbackService.queryUserClickup();
    	if(sysConfig != null) {
    		useful = sysConfig.getConfigValue();
    	}
//    	if(userBasicRecord != null)
//    		qrCode = userBasicRecord.getQrCode();
    	
    	Map<String, String> resultMap = new HashMap<>();
    	resultMap.put("clicked", clicked);
    	resultMap.put("useful", useful);
    	resultMap.put("adTitle", "关注下方二维码，查看当前排队情况，获取更多宝宝相关知识。");
    	resultMap.put("adImage", "images/diagnosis/elephone.png");
    	return WebUtils.buildSuccessResponseMessage(resultMap);
    }
    
    /**
     * 查询反馈选项列表
     * @return
     */
    @PostMapping("/items/query")
    public ResponseMessage queryItems(String systemType) {
    	if(StringUtils.isEmpty(systemType)) {
    		return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
    	}
    	AppType appType = AppType.findByValue(systemType);
    	if(appType == null) {
    		return WebUtils.buildResponseMessage(ResponseStatus.INVALID_VALUE);
    	}
    	List<UserFeedBackItem> itemList = userFeedbackService.listUserFeedBackItem(appType);
    	List<UserFeedBackItemVo> feedbackItems = itemList.stream().map(UserFeedBackItemVo::new).collect(Collectors.toList());
    	return WebUtils.buildSuccessResponseMessage(feedbackItems);
    }
}
