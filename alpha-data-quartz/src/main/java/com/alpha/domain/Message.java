package com.alpha.domain;

/**
 * Created by MR.Wu on 2017-08-16.
 */
public class Message {

    /**
     * 接口返回标识
     */
    private int falg;

    /**
     * 标识对应的描述信息
     */
    private String message;

    /**
     * 数据体
     */
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getFalg() {
        return falg;
    }

    public void setFalg(int falg) {
        this.falg = falg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
