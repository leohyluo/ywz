package com.alpha.commons.api.tencent.offical.dto;

public class AccessTokenDTO {

	/*	
	返回码说明
	-1		系统繁忙，此时请开发者稍候再试
	0		请求成功
	40001	AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性
	40002	请确保grant_type字段值为client_credential
	40164	调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置
 	*/
	private Integer errcode;
	private String access_token;
	private String expires_in;
	private String errmsg;
	//自定义字段,过期时间
	private Long expireTime;
	
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	
	
}
