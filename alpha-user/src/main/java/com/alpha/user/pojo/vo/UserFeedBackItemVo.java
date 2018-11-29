package com.alpha.user.pojo.vo;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.alpha.user.pojo.UserFeedBackItem;

public class UserFeedBackItemVo implements Serializable {

	private static final long serialVersionUID = 8020474462232807499L;

	private String itemName;
	
	private String itemCode;

	public UserFeedBackItemVo() {}
	
	public UserFeedBackItemVo(UserFeedBackItem userFeedBackItem) {
		BeanUtils.copyProperties(userFeedBackItem, this);
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	
}
