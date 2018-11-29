package com.alpha.user.pojo.vo;


import javax.swing.text.ParagraphView;

/**
 * Created by HP on 2018/5/15.\
 * 综合统计数据返回前端数据
 */
public class CountTimesVo {

    private String flag;//标记 AM 上午，Pm 下午
    private Integer cardTimes;//开卡次数
    private Integer scanTimes;//扫码次数
    private Integer useYwzTimes;//进入预问诊次数
    private Integer submitYwzTimes;//完成预问诊次数
    private Integer doctorNum;//医生使用人数
    private Integer ECHimportTimes;//导入次数
    private Integer ECHopenTiems;//打开次数

    private Integer pushAppointmentTimes;//推送预约
    private Integer pushAppointmentSuccessTimes;//推送预约成功
    private Integer pushLiveTimes;//推送现场取号
    private Integer pushLiveSuccessTimes;//推送现场成功
    private Integer wzSuccessTimes;//问诊完成量
    private Integer scanSuccessTimes;//扫码完成量
    private Integer firstVisitTimes;//初诊量
    private Integer firstVisitNoCoverTimes;//初诊未覆盖
    private Integer secondVisitTimes;//复诊


    private Integer pushAppointmentClickTimes;//推送预约成功+点击
    private Integer pushAppointmentSubmitTimes;//推送预约成功+点击+提交
    private Integer pushLiveClickTimes;//推送取号成功+点击
    private Integer pushLiveSubmitTimes;//推送取号成功+点击+提交

    public Integer getPushAppointmentClickTimes() {
        return pushAppointmentClickTimes;
    }

    public void setPushAppointmentClickTimes(Integer pushAppointmentClickTimes) {
        this.pushAppointmentClickTimes = pushAppointmentClickTimes;
    }

    public Integer getPushAppointmentSubmitTimes() {
        return pushAppointmentSubmitTimes;
    }

    public void setPushAppointmentSubmitTimes(Integer pushAppointmentSubmitTimes) {
        this.pushAppointmentSubmitTimes = pushAppointmentSubmitTimes;
    }

    public Integer getPushLiveClickTimes() {
        return pushLiveClickTimes;
    }

    public void setPushLiveClickTimes(Integer pushLiveClickTimes) {
        this.pushLiveClickTimes = pushLiveClickTimes;
    }

    public Integer getPushLiveSubmitTimes() {
        return pushLiveSubmitTimes;
    }

    public void setPushLiveSubmitTimes(Integer pushLiveSubmitTimes) {
        this.pushLiveSubmitTimes = pushLiveSubmitTimes;
    }


    public Integer getPushAppointmentTimes() {
        return pushAppointmentTimes;
    }

    public void setPushAppointmentTimes(Integer pushAppointmentTimes) {
        this.pushAppointmentTimes = pushAppointmentTimes;
    }

    public Integer getPushAppointmentSuccessTimes() {
        return pushAppointmentSuccessTimes;
    }

    public void setPushAppointmentSuccessTimes(Integer pushAppointmentSuccessTimes) {
        this.pushAppointmentSuccessTimes = pushAppointmentSuccessTimes;
    }

    public Integer getPushLiveTimes() {
        return pushLiveTimes;
    }

    public void setPushLiveTimes(Integer pushLiveTimes) {
        this.pushLiveTimes = pushLiveTimes;
    }

    public Integer getPushLiveSuccessTimes() {
        return pushLiveSuccessTimes;
    }

    public void setPushLiveSuccessTimes(Integer pushLiveSuccessTimes) {
        this.pushLiveSuccessTimes = pushLiveSuccessTimes;
    }

    public Integer getWzSuccessTimes() {
        return wzSuccessTimes;
    }

    public void setWzSuccessTimes(Integer wzSuccessTimes) {
        this.wzSuccessTimes = wzSuccessTimes;
    }

    public Integer getScanSuccessTimes() {
        return scanSuccessTimes;
    }

    public void setScanSuccessTimes(Integer scanSuccessTimes) {
        this.scanSuccessTimes = scanSuccessTimes;
    }

    public Integer getFirstVisitTimes() {
        return firstVisitTimes;
    }

    public void setFirstVisitTimes(Integer firstVisitTimes) {
        this.firstVisitTimes = firstVisitTimes;
    }

    public Integer getFirstVisitNoCoverTimes() {
        return firstVisitNoCoverTimes;
    }

    public void setFirstVisitNoCoverTimes(Integer firstVisitNoCoverTimes) {
        this.firstVisitNoCoverTimes = firstVisitNoCoverTimes;
    }

    public Integer getSecondVisitTimes() {
        return secondVisitTimes;
    }

    public void setSecondVisitTimes(Integer secondVisitTimes) {
        this.secondVisitTimes = secondVisitTimes;
    }



    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getCardTimes() {
        return cardTimes;
    }

    public void setCardTimes(Integer cardTimes) {
        this.cardTimes = cardTimes;
    }

    public Integer getScanTimes() {
        return scanTimes;
    }

    public void setScanTimes(Integer scanTimes) {
        this.scanTimes = scanTimes;
    }

    public Integer getUseYwzTimes() {
        return useYwzTimes;
    }

    public void setUseYwzTimes(Integer useYwzTimes) {
        this.useYwzTimes = useYwzTimes;
    }

    public Integer getSubmitYwzTimes() {
        return submitYwzTimes;
    }

    public void setSubmitYwzTimes(Integer submitYwzTimes) {
        this.submitYwzTimes = submitYwzTimes;
    }

    public Integer getDoctorNum() {
        return doctorNum;
    }

    public void setDoctorNum(Integer doctorNum) {
        this.doctorNum = doctorNum;
    }

    public Integer getECHimportTimes() {
        return ECHimportTimes;
    }

    public void setECHimportTimes(Integer ECHimportTimes) {
        this.ECHimportTimes = ECHimportTimes;
    }

    public Integer getECHopenTiems() {
        return ECHopenTiems;
    }

    public void setECHopenTiems(Integer ECHopenTiems) {
        this.ECHopenTiems = ECHopenTiems;
    }


}
