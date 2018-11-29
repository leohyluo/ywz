package com.alpha.commons.core.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/5.
 */
public class NJResponse<T> implements Serializable{

    private String result;
    private String message;
    private List<T> data=new ArrayList<>();
    private String funName;
    private String outString;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getOutString() {
        return outString;
    }

    public void setOutString(String outString) {
        this.outString = outString;
    }

}
