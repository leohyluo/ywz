package com.alpha.his.pojo.dto;

import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNotice;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfo;

/**
 * 用户住院数据传输类
 */
public class UserHospitalized {
    //医院编码
    private String hospitalCode;
    //门诊号
    private String outPatientNo;
    //住院号
    private String hosno;
    //门诊患者信息
    private HospitalizedPatientInfo hospitalizedPatientInfo;
    //住院通知单信息
    private HospitalizedNotice hospitalizedNotice;
    //普通患儿信息
    private HospitalizedCommonIllChild hospitalizedCommonIllChild;
    //新生儿信息
    private HospitalizedNewIllChild hospitalizedNewIllChild;

    public HospitalizedPatientInfo getHospitalizedPatientInfo() {
        return hospitalizedPatientInfo;
    }

    public void setHospitalizedPatientInfo(HospitalizedPatientInfo hospitalizedPatientInfo) {
        this.hospitalizedPatientInfo = hospitalizedPatientInfo;
    }

    public HospitalizedNotice getHospitalizedNotice() {
        return hospitalizedNotice;
    }

    public void setHospitalizedNotice(HospitalizedNotice hospitalizedNotice) {
        this.hospitalizedNotice = hospitalizedNotice;
    }

    public HospitalizedCommonIllChild getHospitalizedCommonIllChild() {
        return hospitalizedCommonIllChild;
    }

    public void setHospitalizedCommonIllChild(HospitalizedCommonIllChild hospitalizedCommonIllChild) {
        this.hospitalizedCommonIllChild = hospitalizedCommonIllChild;
    }

    public HospitalizedNewIllChild getHospitalizedNewIllChild() {
        return hospitalizedNewIllChild;
    }

    public void setHospitalizedNewIllChild(HospitalizedNewIllChild hospitalizedNewIllChild) {
        this.hospitalizedNewIllChild = hospitalizedNewIllChild;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo;
    }

    public String getHosno() {
        return hosno;
    }

    public void setHosno(String hosno) {
        this.hosno = hosno;
    }
}
