package com.alpha.commons.web;

import java.io.Serializable;

import com.alpha.commons.util.JsonUtils;

public class ResponseMessage implements Serializable {

	/**
	 * 
	 */

	public static final long serialVersionUID = -2297332672297066370L;
	protected int code;
	protected Object data="";
	protected String msg;

	public ResponseMessage() { 
		this(ResponseStatus.SUCCESS);
	}

	public ResponseMessage(ResponseStatus status) {
		this(null, status);
	}
	public ResponseMessage(Object data) {
		this(data, ResponseStatus.SUCCESS);
	}

	public ResponseMessage(String data, ResponseStatus status) {
		this.setCode(status.code());
		this.setMsg(status.message());
		this.setData(data);
	}
	
	public ResponseMessage(Object data, ResponseStatus status) {
		this.setData(data);
		this.setCode(status.code());
		this.setMsg(status.message());
	}
	
	public ResponseMessage(int code, String error) {
		this.setCode(code);
		this.setMsg(error);
	}

	public int getCode() {
		return code;
	}

	public ResponseMessage setCode(int code) { 
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResponseMessage setStatus(ResponseStatus status) {
		this.setCode(status.code());
		this.setMsg(status.message());
		return this; 
	}
	
	public Object getData() {
		return data;
	}
	
	public ResponseMessage setData(Object data) {
		this.data = data;
		return this;
	}


	public String toJsonString() {
		return JsonUtils.toJsonString(this);
	}
	
}
