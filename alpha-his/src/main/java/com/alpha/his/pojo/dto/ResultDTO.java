package com.alpha.his.pojo.dto;

import com.alpha.commons.web.ResponseStatus;

public class ResultDTO {

    private String code;
    private String msg;
    private String sign;

    public ResultDTO() {}

    public ResultDTO(ResponseStatus responseStatus, String sign) {
        this.code = responseStatus.code() + "";
        this.msg = responseStatus.message() + "";
        this.sign = sign;
    }

    public ResultDTO(String code, String msg, String sign) {
        this.code = code;
        this.msg = msg;
        this.sign = sign;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
}
