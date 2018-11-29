package com.alpha.his.service.etyy;

import com.alpha.commons.core.service.IBaseService;
import com.alpha.server.rpc.his.pojo.*;
import com.alpha.his.pojo.dto.UserHospitalized;

import java.util.List;

/**
 * 住院业务接口类
 */
public interface HospitalizedPatientInfoService extends IBaseService<HospitalizedPatientInfoNew,Long> {

//    /**
//     * 保存住院信息
//     * @param hospitalizedPatientInfo 门诊患者信息
//     * @param hospitalizedNotice 住院通知单信息
//     * @param hospitalizedCommonIllChild 普通患儿信息
//     * @param hospitalizedNewIllChild 新生儿信息
//     */
    void saveUserHospitalizedInfo(String hospitalCode, String outPatientNo, String hosNo, HospitalizedPatientInfo hospitalizedPatientInfo, HospitalizedNotice hospitalizedNotice,
                                  HospitalizedCommonIllChild hospitalizedCommonIllChild, HospitalizedNewIllChild hospitalizedNewIllChild);

    /**
     * 查看住院详细信息
     * @param hospitalCode
     * @param hosNo
     * @return
     */
    UserHospitalized getUserHospitalizedInfo(String hospitalCode, String hosNo);

    /**
     * 查看住院列表
     * @param hospitalCode
     * @param outPatientNo
     * @return
     */
    List<UserHospitalized> getUserHospitalizedInfoList(String hospitalCode, String outPatientNo);

    Long savePatientInfoAndModifyNotice(HospitalizedPatientInfoNew hospitalizedPatientInfo,HospitalizedNotice notice);

    Long modifyPatientInfoAndModifyNotice(HospitalizedPatientInfoNew hospitalizedPatientInfo,HospitalizedNotice notice);

    /**
     * 根据通知单编码获取患者信息
     * @param noticeId
     * @return
     */
    HospitalizedPatientInfoNew getByNoticeId(String noticeId);
}
