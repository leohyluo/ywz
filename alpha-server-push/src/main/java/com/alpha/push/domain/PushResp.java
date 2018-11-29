package com.alpha.push.domain;

/**
 * Created by MR.Wu on 2018-06-25.
 * 推送信息返回体
 */
public class PushResp {

    /**
     * 0:推送失败  1:推送成功
     */
    private int result;

    /**
     * 描述
     */
    private String desc;

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

    @Override
    public String toString() {
        return "PushResp{" +
                "result=" + result +
                ", desc='" + desc + '\'' +
                '}';
    }
}
