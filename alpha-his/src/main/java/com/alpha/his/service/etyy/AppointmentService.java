package com.alpha.his.service.etyy;

import com.alpha.commons.core.pojo.HisRegisterYygh;

/**
 * 预约模块业务接口
 */
public interface AppointmentService {

    String getH5Url();

    /**
     * 给现场号推送预问诊消息
     *
     * @param livePatient
     */
    void noticeLivePatient(HisRegisterYygh livePatient);

    /**
     * 给预约号推送预问诊消息
     *
     * @param appointmentPatient
     */
    void noticeAppointmentPatient(HisRegisterYygh appointmentPatient);

    void createUserInfo(HisRegisterYygh registerInfo);

    void createHisRegisterRecord(HisRegisterYygh registerInfo);

    /**
     * 现在号消息模板
     *
     * @param livePatient
     * @return
     */
//    default PushInfo getLiveTemplate(HisRegisterYygh livePatient) {
//        String url = getH5Url().concat("#/preProcess");
//        PushInfo pushInfo = new PushInfo();
//
//        String birthDay = livePatient.getBirthday();
//        try {
//            if(StringUtils.isNotEmpty(birthDay)) {
//                Date date = DateUtils.string2Date(livePatient.getBirthday());
//                birthDay = DateUtils.date2String(date, DateUtils.DATE_FORMAT);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        url = url.concat("?pp=").concat(livePatient.getPno()).concat("|").concat(livePatient.getOutPatientNo()).concat("|")
//                .concat(livePatient.getSex()).concat("|").concat(PreDiagnosisChannel.OFFICEACCOUNT.getValue()).concat("|").concat(birthDay)
//                .concat("|").concat(livePatient.getType().toString());
//        RegisterType registerType = RegisterType.findByValue(livePatient.getType().toString());
//
//        pushInfo.setTitle("请就诊前完成预问诊，若有不明之处，请咨询分诊台护士。");
//        pushInfo.setDep(livePatient.getDeptName());
//        pushInfo.setCardNo(livePatient.getCardNo());
//        pushInfo.setPhone(livePatient.getPhone());
//        pushInfo.setUserName(livePatient.getPatientName());
//        pushInfo.setDepDocter(livePatient.getDoctorName());
//        pushInfo.setWatchingTime(livePatient.getVisitTime());
//        pushInfo.setPno(livePatient.getPno());
//        pushInfo.setUrl(url);
//        pushInfo.setType(registerType);
//
//        return pushInfo;
//    }
//
//    /**
//     * 预约号消息模板
//     *
//     * @param livePatient
//     * @return
//     */
//    default PushInfo getAppointmentTemplate(HisRegisterYygh livePatient) {
//        String url = getH5Url().concat("#/preProcess");
//        PushInfo pushInfo = new PushInfo();
//
//        //preProcess?pno=24273376|outPatientNo=16112010049|gender=1|channel=2|birth=2016-03-15
//        String birthDay = livePatient.getBirthday();
//        try {
//            if(StringUtils.isNotEmpty(birthDay)) {
//                Date date = DateUtils.string2Date(livePatient.getBirthday());
//                birthDay = DateUtils.date2String(date, DateUtils.DATE_FORMAT);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        url = url.concat("?pp=").concat(livePatient.getYno()).concat("|").concat(livePatient.getOutPatientNo()).concat("|")
//                .concat(livePatient.getSex()).concat("|").concat(PreDiagnosisChannel.OFFICEACCOUNT.getValue()).concat("|").concat(birthDay)
//                .concat("|").concat(livePatient.getType().toString());
//
//        RegisterType registerType = RegisterType.findByValue(livePatient.getType().toString());
//
//        pushInfo.setTitle("为了让医生提前了解您宝宝的病情，请就诊前完成预问诊。");
//        pushInfo.setDep(livePatient.getDeptName());
//        pushInfo.setCardNo(livePatient.getCardNo());
//        pushInfo.setPhone(livePatient.getPhone());
//        pushInfo.setUserName(livePatient.getPatientName());
//        pushInfo.setDepDocter(livePatient.getDoctorName());
//        pushInfo.setWatchingTime(livePatient.getVisitTime());
//        pushInfo.setPno(livePatient.getPno());
//        pushInfo.setUrl(url);
//        pushInfo.setType(registerType);
//        return pushInfo;
//    }
//
//    /**
//     * 病情核对消息模板
//     *
//     * @param livePatient
//     * @return
//     */
//    default PushInfo getConfirmTemplate(HisRegisterYygh livePatient, UserBasicRecord userBasicRecord) {
//        String url = getH5Url().concat("#/push/").concat(userBasicRecord.getUserId().toString()).concat("/").concat(userBasicRecord.getDiagnosisId().toString())
//                .concat("/").concat(livePatient.getOutPatientNo()).concat("/").concat(livePatient.getPno());
//        //preProcess?pno=24273376|outPatientNo=16112010049|gender=1|channel=2|birth=2016-03-15
//       /* url = url.concat("?pp=").concat(livePatient.getPno()).concat("|").concat(livePatient.getOutPatientNo()).concat("|")
//                .concat(livePatient.getSex()).concat("|").concat(PreDiagnosisChannel.OFFICEACCOUNT.getValue()).concat("|").concat(livePatient.getBirthday())
//                .concat("|").concat(livePatient.getType().toString());*/
//
//        RegisterType registerType = RegisterType.findByValue(livePatient.getType().toString());
//
//        PushInfo pushInfo = new PushInfo();
//        pushInfo.setTitle("请核对并更新您宝宝的病情。");
//        pushInfo.setDep(livePatient.getDeptName());
//        pushInfo.setCardNo(livePatient.getCardNo());
//        pushInfo.setPhone(livePatient.getPhone());
//        pushInfo.setUserName(livePatient.getPatientName());
//        pushInfo.setDepDocter(livePatient.getDoctorName());
//        pushInfo.setWatchingTime(livePatient.getVisitTime());
//        pushInfo.setPno(livePatient.getPno());
//        pushInfo.setUrl(url);
//        pushInfo.setType(registerType);
//
//        return pushInfo;
//    }

}
