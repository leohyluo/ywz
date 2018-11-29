package com.alpha.commons.exception;


import com.alpha.commons.web.ResponseStatus;

/**
 * 业务异常
 *
 * 说明：在业务代码中因业务需要可抛出ServiceException类型异常
 */
public class ServiceException extends RuntimeException {

    //错误码
    private ResponseStatus resultEnum;

    //异常内容
    private Object content;

    public ServiceException() {
        // TODO Auto-generated constructor stub
    }

    public ServiceException(ResponseStatus resultEnum, Object content) {
        this.resultEnum = resultEnum;
        this.content = content;
    }

    public ServiceException(ResponseStatus responseStatus) {
        this.resultEnum = responseStatus;
        this.content = responseStatus.message();
    }

    public ResponseStatus getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResponseStatus resultEnum) {
        this.resultEnum = resultEnum;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
