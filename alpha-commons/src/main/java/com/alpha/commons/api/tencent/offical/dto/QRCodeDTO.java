package com.alpha.commons.api.tencent.offical.dto;

public class QRCodeDTO {

	public static final int NEWSMSG = 1;            //图文消息  
    private boolean isSuccess;  
    private Object obj;  
    private int type;  
    private String msg;
    
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
    
    
}
