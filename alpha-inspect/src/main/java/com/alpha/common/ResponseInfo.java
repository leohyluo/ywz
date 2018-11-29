package com.alpha.common;


import com.alpha.commons.web.ResponseStatus;

/**
 * Created by Administrator on 2018/8/16.
 */
public class ResponseInfo<T> {

    private T resultInfo;
    private Integer code;
    private String msg;

    public ResponseInfo(){
        this.code=ResponseStatus.SUCCESS.code();
        this.msg=ResponseStatus.SUCCESS.message();
    };
    
    public ResponseInfo(T data,Integer code){
        this.resultInfo=data;
        this.code=code;
        this.msg= ResponseStatus.findByCode(code).message();
    };
    public T getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(T data) {
        this.resultInfo = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
        this.msg= ResponseStatus.findByCode(code).message();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
