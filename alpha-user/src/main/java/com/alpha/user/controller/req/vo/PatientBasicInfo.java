package com.alpha.user.controller.req.vo;

public class PatientBasicInfo {

    //用户对象
    private PatientInfo userInfo;

    //渠道标识
    private int inType;

    public PatientInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(PatientInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getInType() {
        return inType;
    }

    public void setInType(int inType) {
        this.inType = inType;
    }


}
