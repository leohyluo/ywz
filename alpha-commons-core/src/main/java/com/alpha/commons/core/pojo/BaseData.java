package com.alpha.commons.core.pojo;

import com.alpha.commons.web.ResponseStatus;

/**
 * Created by Administrator on 2018/3/15.
 */
public class BaseData {
    private Integer code;
    private String msg;
    private String sign;
    public BaseData(){
        this.code=ResponseStatus.SUCCESS.code();
        this.msg=ResponseStatus.SUCCESS.message();
    }
    public BaseData(ResponseStatus status){
        this.code=status.code();
        this.msg=status.message();
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setCode(ResponseStatus status){
        this.code=status.code();
        this.msg=status.message();
    }

}
