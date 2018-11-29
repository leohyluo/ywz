package com.alpha.user.pojo.vo;

import java.util.List;

/**
 * Created by HP on 2018/5/15.
 * 医生人数，
 */
public class DocDetailVo {

    private String flag;

    public List<DoctorTimes> getDoctorTimes() {
        return doctorTimes;
    }

    public void setDoctorTimes(List<DoctorTimes> doctorTimes) {
        this.doctorTimes = doctorTimes;
    }

    private List<DoctorTimes> doctorTimes;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }




}
