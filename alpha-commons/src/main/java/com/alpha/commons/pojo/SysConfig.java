package com.alpha.commons.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_config")
public class SysConfig implements Serializable {

	private static final long serialVersionUID = 8020474462232807499L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "config_key")
	private String configKey;
	
	@Column(name = "config_value")
	private String configValue;

	@Column(name = "config_desc")
	private String configDesc;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "update_time")
	private Date updateTime;

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
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

	@Override
	public String toString() {
		return "SysConfig{" +
				"id=" + id +
				", configKey='" + configKey + '\'' +
				", configValue='" + configValue + '\'' +
				", configDesc='" + configDesc + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
