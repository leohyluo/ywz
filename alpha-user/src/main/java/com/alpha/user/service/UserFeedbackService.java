package com.alpha.user.service;

import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.System;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.server.rpc.user.pojo.UserFeedback;
import com.alpha.user.pojo.UserFeedBackItem;

import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/10/12.
 */
public interface UserFeedbackService {

    /**
     * 保存用户反馈信息
     *
     * @param userFeedback
     */
    Map<String, String> saveUserFeedback(UserFeedback userFeedback);
    
    /**
	 * 获取用户点赞信息
	 * @return
	 */
	SysConfig queryUserClickup();
    
    /**
	 * 更新用户点赞次数
	 * @return
	 */
	String updateUserClickup();
	
	/**
	 * 查询反馈选项
	 * @return
	 */
	List<UserFeedBackItem> listUserFeedBackItem(AppType appType);
	
	/**
	 * 查询用户反馈
	 * @param diagnosisId
	 * @return
	 */
	List<UserFeedback> listUserFeedback(Long diagnosisId);
}
