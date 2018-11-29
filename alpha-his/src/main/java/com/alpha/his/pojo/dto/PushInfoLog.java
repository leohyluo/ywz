package com.alpha.his.pojo.dto;

import java.io.Serializable;

/**
 * Created by MR.Wu on 2018-06-27.
 */
public class PushInfoLog implements Serializable {

    private static final long serialVersionUID = -8209734395138314538L;

    private int id;

    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 挂号
     */
    private String pNo;

    private int result;

    private String desc;

    private String hashCode;

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getpNo() {
        return pNo;
    }

    public void setpNo(String pNo) {
        this.pNo = pNo;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
