package com.alpha.user.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_feedback_item")
public class UserFeedBackItem implements Serializable {

	private static final long serialVersionUID = 8020474462232807499L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "item_name")
	private String itemName;
	
	@Column(name = "item_code")
	private String itemCode;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "update_time")
	private Date updateTime;
	
	@Column(name = "system_type")
	private String systemType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	
}
