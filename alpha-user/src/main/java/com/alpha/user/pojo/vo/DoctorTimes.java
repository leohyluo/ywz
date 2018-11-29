package com.alpha.user.pojo.vo;

/**
 * Created by HP on 2018/5/15.
 */
public class DoctorTimes {

    private String docName; //医生名字
    private Integer times;//导入次数
    private Integer showTimes;//展现次数

    public Integer getShowTimes() {
        return showTimes;
    }
    public void setShowTimes(Integer showTimes) {
        this.showTimes = showTimes;
    }
    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }


}
