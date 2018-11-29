package com.alpha.server.rpc.his.vo;

import com.alpha.server.rpc.his.pojo.HisDiagnosisRecord;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserInfo;

public class MedicineRecorcdVo {

    private UserInfo userInfo;
    private UserBasicRecord userBasicRecord;
    private HisDiagnosisRecord hisDiagnosisRecord;

    public MedicineRecorcdVo() {}

    public MedicineRecorcdVo(UserInfo userInfo, UserBasicRecord userBasicRecord, HisDiagnosisRecord hisDiagnosisRecord) {
        this.userInfo = userInfo;
        this.userBasicRecord = userBasicRecord;
        this.hisDiagnosisRecord = hisDiagnosisRecord;
    }

    public UserBasicRecord getUserBasicRecord() {
        return userBasicRecord;
    }

    public void setUserBasicRecord(UserBasicRecord userBasicRecord) {
        this.userBasicRecord = userBasicRecord;
    }

    public HisDiagnosisRecord getHisDiagnosisRecord() {
        return hisDiagnosisRecord;
    }

    public void setHisDiagnosisRecord(HisDiagnosisRecord hisDiagnosisRecord) {
        this.hisDiagnosisRecord = hisDiagnosisRecord;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
