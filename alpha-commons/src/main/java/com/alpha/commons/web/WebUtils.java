package com.alpha.commons.web;

public final class WebUtils {
	private WebUtils() {
	}


	public static ResponseMessage buildSuccessResponseMessage() {
		ResponseMessage message = new ResponseMessage(ResponseStatus.SUCCESS);
		return message;
	}

	public static ResponseMessage buildSuccessResponseMessage(String data) {
		ResponseMessage message = new ResponseMessage(data, ResponseStatus.SUCCESS);
		return message;
	}

	public static ResponseMessage buildResponseMessage(ResponseStatus responseStatus, String data) {
		ResponseMessage message = new ResponseMessage(data, responseStatus);
		return message;
	}

	public static ResponseMessage buildResponseMessage(int code, String error) {
		ResponseMessage message = new ResponseMessage(code, error);
		return message;
	}

	public static ResponseMessage buildResponseMessage(ResponseStatus responseStatus) {
		ResponseMessage message = new ResponseMessage(responseStatus);
		return message;
	}

	public static ResponseMessage buildResponseMessage(ResponseStatus responseStatus, Object data) {
		ResponseMessage message = new ResponseMessage(data, responseStatus);
		return message;
	}

	public static ResponseMessage buildSuccessResponseMessage(Object data) {
		ResponseMessage message = new ResponseMessage(data, ResponseStatus.SUCCESS);
		return message;
	}
}
