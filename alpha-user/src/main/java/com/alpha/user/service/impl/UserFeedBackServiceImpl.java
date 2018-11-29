package com.alpha.user.service.impl;

import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.SysConfigKey;
import com.alpha.commons.enums.System;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.server.rpc.user.pojo.UserFeedback;
import com.alpha.user.dao.SysConfigDao;
import com.alpha.user.dao.UserFeedBackItemDao;
import com.alpha.user.dao.UserFeedbackDao;
import com.alpha.user.pojo.UserFeedBackItem;
import com.alpha.user.service.UserFeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserFeedBackServiceImpl implements UserFeedbackService {

    @Resource
    private UserFeedbackDao userFeedbackDao;
    @Resource
    private SysConfigDao sysConfigDao;
    @Resource
    private UserFeedBackItemDao userFeedBackItemDao;

    /**
     * 保存用户反馈信息
     *
     * @param userFeedback
     */
    public Map<String, String> saveUserFeedback(UserFeedback userFeedback) {
    	Long diagnosisId = userFeedback.getDiagnosisId();
    	Integer useType = userFeedback.getUseType();
    	List<UserFeedback> feedbackList = userFeedbackDao.listUserFeedback(diagnosisId);
    	UserFeedback feedback = CollectionUtils.isEmpty(feedbackList) ? null : feedbackList.get(0);
    	Map<String, String> resultMap = new HashMap<>();
    	
    	if(useType == 1) {	//点赞
    		if(feedback == null) {
    			userFeedback.setUseful(1);
    			userFeedback.setCreateTime(new Date());
    			userFeedbackDao.insert(userFeedback);
    			String usefulCount = updateUserClickup();
    			resultMap.put("clicked", "1");
    			resultMap.put("useful", usefulCount);
    		} else {
    			Integer userful = feedback.getUseful() == null ? 0 : feedback.getUseful();
    			if(userful != 1) {
    				feedback.setUseful(1);
    				feedback.setCreateTime(new Date());
        			userFeedbackDao.update(feedback);
        			String usefulCount = updateUserClickup();
        			resultMap.put("clicked", "1");
        			resultMap.put("useful", usefulCount);
    			}
    		}
    	} else if (useType == 2) {	//反馈
    		if(feedback == null) {
    			userFeedback.setUseful(null);
    			userFeedback.setCreateTime(new Date());
    			userFeedbackDao.insert(userFeedback);
    		} else {
    			feedback.setItemCode(userFeedback.getItemCode());
    			feedback.setContent(userFeedback.getContent());
    			userFeedbackDao.update(feedback);
    		}
    	}
    	return resultMap;
    }
    
    @Override
    public SysConfig queryUserClickup() {
    	return sysConfigDao.querySysConfig(SysConfigKey.USEFUL.getValue());
    }

	@Override
	public String updateUserClickup() {
		String newClickCount = "1";
		SysConfig sysConfig = this.queryUserClickup();
    	if(sysConfig == null) {
    		Date now = new Date();
    		sysConfig = new SysConfig();
    		sysConfig.setConfigKey(SysConfigKey.USEFUL.getValue());
    		sysConfig.setConfigValue("1");
    		sysConfig.setCreateTime(now);
    		sysConfig.setUpdateTime(now);
    		sysConfigDao.insert(sysConfig);
    	} else {
    		Integer clickCount = Integer.parseInt(sysConfig.getConfigValue()) + 1;
    		newClickCount = String.valueOf(clickCount); 
    		sysConfig.setConfigValue(newClickCount);
    		sysConfigDao.update(sysConfig);
    	}
    	return newClickCount;
	}

	@Override
	public List<UserFeedBackItem> listUserFeedBackItem(AppType appType) {
		return userFeedBackItemDao.listUserFeedBackItem(appType);
	}

	@Override
	public List<UserFeedback> listUserFeedback(Long diagnosisId) {
		return userFeedbackDao.listUserFeedback(diagnosisId);
	}

}
