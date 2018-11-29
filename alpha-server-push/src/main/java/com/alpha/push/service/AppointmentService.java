package com.alpha.push.service;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.PreDiagnosisChannel;
import com.alpha.commons.enums.RegisterType;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.push.domain.HospitalDept;
import com.alpha.push.domain.MessageEvent;
import com.alpha.push.domain.PushInfo;
import com.alpha.push.domain.UserBasicRecord;

import java.util.Date;

/**
 * 预约模块业务接口
 */
public interface AppointmentService {

    String getH5Url();

    String getHospitalCode();

    /**
     * 给现场号推送预问诊消息
     *
     * @param livePatient
     */
    MessageEvent noticeLivePatient(HisRegisterYygh livePatient);

    /**
     * 给预约号推送预问诊消息
     *
     * @param appointmentPatient
     */
    MessageEvent noticeAppointmentPatient(HisRegisterYygh appointmentPatient);


    /**
     * 现在号消息模板
     *
     * @param livePatient
     * @return
     */
    default PushInfo getLiveTemplate(HisRegisterYygh livePatient) {
        String url = getH5Url().concat("#/preProcess");
        PushInfo pushInfo = new PushInfo();

        String birthDay = livePatient.getBirthday();
        try {
            if(StringUtils.isNotEmpty(birthDay)) {
                Date date = DateUtils.string2Date(livePatient.getBirthday());
                birthDay = DateUtils.date2String(date, DateUtils.DATE_FORMAT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String deptName = livePatient.getDeptName();
        String appType = AppType.CHILD.getValue();
        if (StringUtils.isNotEmpty(deptName)) {
            String hospitalCode = getHospitalCode();
            HospitalDeptService hospitalDeptService = SpringContextHolder.getBean("hospitalDeptServiceImpl");
            HospitalDept dept = hospitalDeptService.getByHospitalCodeAndDeptName(hospitalCode, deptName);
            if (dept != null) {
                appType = dept.getDeptCode();
            }
        }

        String diagnosisId = "-1";
        url = url.concat("?pp=").concat(livePatient.getPno()).concat("|").concat(livePatient.getOutPatientNo()).concat("|")
                .concat(livePatient.getSex()).concat("|").concat(PreDiagnosisChannel.OFFICEACCOUNT.getValue()).concat("|").concat(birthDay)
                .concat("|").concat(livePatient.getType().toString()).concat("|").concat(diagnosisId).concat("|").concat(appType);
        RegisterType registerType = RegisterType.findByValue(livePatient.getType().toString());

        pushInfo.setTitle(livePatient.getDesc());
        pushInfo.setDep(livePatient.getDeptName());
        pushInfo.setCardNo(livePatient.getCardNo());
        pushInfo.setPhone(livePatient.getPhone());
        pushInfo.setUserName(livePatient.getPatientName());
        pushInfo.setDepDocter(livePatient.getDoctorName());
        pushInfo.setWatchingTime(livePatient.getVisitTime());
        pushInfo.setPno(livePatient.getPno());
        pushInfo.setUrl(url);
        pushInfo.setType(registerType);
        pushInfo.setFooter(livePatient.getFooter());

        return pushInfo;
    }

    /**
     * 预约号消息模板
     *
     * @param livePatient
     * @return
     */
    default PushInfo getAppointmentTemplate(HisRegisterYygh livePatient) {
        String url = getH5Url().concat("#/preProcess");
        PushInfo pushInfo = new PushInfo();

        //preProcess?pno=24273376|outPatientNo=16112010049|gender=1|channel=2|birth=2016-03-15
        String birthDay = livePatient.getBirthday();
        try {
            if(StringUtils.isNotEmpty(birthDay)) {
                Date date = DateUtils.string2Date(livePatient.getBirthday());
                birthDay = DateUtils.date2String(date, DateUtils.DATE_FORMAT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String diagnosisId = "-1";
        String deptName = livePatient.getDeptName();
        String appType = AppType.CHILD.getValue();
        if (StringUtils.isNotEmpty(deptName)) {
            String hospitalCode = getHospitalCode();
            HospitalDeptService hospitalDeptService = SpringContextHolder.getBean("hospitalDeptServiceImpl");
            HospitalDept dept = hospitalDeptService.getByHospitalCodeAndDeptName(hospitalCode, deptName);
            if (dept != null) {
                appType = dept.getDeptCode();
            }
        }
        url = url.concat("?pp=").concat(livePatient.getYno()).concat("|").concat(livePatient.getOutPatientNo()).concat("|")
                .concat(livePatient.getSex()).concat("|").concat(PreDiagnosisChannel.OFFICEACCOUNT.getValue()).concat("|").concat(birthDay)
                .concat("|").concat(livePatient.getType().toString()).concat("|").concat(diagnosisId).concat("|").concat(appType);

        RegisterType registerType = RegisterType.findByValue(livePatient.getType().toString());

        pushInfo.setTitle(livePatient.getDesc());
        pushInfo.setDep(livePatient.getDeptName());
        pushInfo.setCardNo(livePatient.getCardNo());
        pushInfo.setPhone(livePatient.getPhone());
        pushInfo.setUserName(livePatient.getPatientName());
        pushInfo.setDepDocter(livePatient.getDoctorName());
        pushInfo.setWatchingTime(livePatient.getVisitTime());
        pushInfo.setPno(livePatient.getPno());
        pushInfo.setUrl(url);
        pushInfo.setType(registerType);
        pushInfo.setFooter(livePatient.getFooter());
        return pushInfo;
    }

    /**
     * 病情核对消息模板
     *
     * @param livePatient
     * @return
     */
    default PushInfo getConfirmTemplate(HisRegisterYygh livePatient, UserBasicRecord userBasicRecord) {
        /*String url = getH5Url().concat("#/push/").concat(userBasicRecord.getUserId().toString()).concat("/").concat(userBasicRecord.getDiagnosisId().toString())
                .concat("/").concat(livePatient.getOutPatientNo()).concat("/").concat(livePatient.getPno());*/

        String birthDay = livePatient.getBirthday();
        try {
            if(StringUtils.isNotEmpty(birthDay)) {
                Date date = DateUtils.string2Date(livePatient.getBirthday());
                birthDay = DateUtils.date2String(date, DateUtils.DATE_FORMAT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String diagnosisId = userBasicRecord.getDiagnosisId().toString();
        String url = getH5Url().concat("#/preProcess");
        url = url.concat("?pp=").concat(livePatient.getPno()).concat("|").concat(livePatient.getOutPatientNo()).concat("|")
                .concat(livePatient.getSex()).concat("|").concat(PreDiagnosisChannel.OFFICEACCOUNT.getValue()).concat("|").concat(birthDay)
                .concat("|").concat(livePatient.getType().toString()).concat("|").concat(diagnosisId);

        RegisterType registerType = RegisterType.findByValue(livePatient.getType().toString());

        PushInfo pushInfo = new PushInfo();
        pushInfo.setTitle(livePatient.getDesc());
        pushInfo.setDep(livePatient.getDeptName());
        pushInfo.setCardNo(livePatient.getCardNo());
        pushInfo.setPhone(livePatient.getPhone());
        pushInfo.setUserName(livePatient.getPatientName());
        pushInfo.setDepDocter(livePatient.getDoctorName());
        pushInfo.setWatchingTime(livePatient.getVisitTime());
        pushInfo.setPno(livePatient.getPno());
        pushInfo.setUrl(url);
        pushInfo.setType(registerType);
        pushInfo.setFooter(livePatient.getFooter());

        return pushInfo;
    }

}
