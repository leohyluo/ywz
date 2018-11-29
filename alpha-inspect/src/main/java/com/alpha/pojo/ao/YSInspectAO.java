package com.alpha.pojo.ao;

/**
 * Created by Administrator on 2018/8/16.
 */
public class YSInspectAO {

    private String startTime;
    private String endTime;
    private String medicalCardNo;
    private Integer pageSize;
    private Integer page;

    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMedicalCardNo() {
        return medicalCardNo;
    }

    public void setMedicalCardNo(String medicalCardNo) {
        this.medicalCardNo = medicalCardNo;
    }


}
