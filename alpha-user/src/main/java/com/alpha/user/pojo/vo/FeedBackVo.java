package com.alpha.user.pojo.vo;

import java.util.List;

public class FeedBackVo {

	   
    //点赞次数
    private String usefulCount;
    
    //反馈选项
    private List<UserFeedBackItemVo> feedbackItems;

	public String getUsefulCount() {
		return usefulCount;
	}

	public void setUsefulCount(String usefulCount) {
		this.usefulCount = usefulCount;
	}

	public List<UserFeedBackItemVo> getFeedbackItems() {
		return feedbackItems;
	}

	public void setFeedbackItems(List<UserFeedBackItemVo> feedbackItems) {
		this.feedbackItems = feedbackItems;
	}
    
    
}
