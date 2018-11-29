package com.alpha.his.service.etyy;


import com.alpha.commons.core.pojo.HospitalizedNoticeNew;
import com.alpha.commons.core.pojo.HospitalizedPatientInfoNew1;
import com.alpha.his.pojo.dto.UserHospitalized;

import java.util.List;

/**
 * 住院业务接口类
 */
public interface HospitalizedService {

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

    /**
     * 获取住院患者 门诊信息+ 住院信息
     * @param outPatientNo   门诊号码
     * @return
     */

    HospitalizedPatientInfoNew1 getPatientHospitalizedInfoNew(String outPatientNo);

    /**
     * 模拟his获取住院患者信息
     * @param outPatientNo   门诊号码
     * @return
     */

    HospitalizedPatientInfoNew1 mockPatientHospitalizedInfoNew(String outPatientNo);

    /**
     * 根据门诊号 获取入院通知书
     * @param outPatientNo
     * @return
     */
    HospitalizedNoticeNew getHospitalizedNotice(String noticeId,String outPatientNo);

    /**
     * 模拟his获取入院通知书
     * @param outPatientNo
     * @return
     */
    HospitalizedNoticeNew mockHospitalizedNotice(String outPatientNo);

    /**
     * 预问诊 扫码卡号自动填充 门诊号
     * @param cardNo
     * @return
     */
    String outPatientNum(String cardNo);

    /**
     * 根据通知单编码查询患者信息
     * @param noticeId   门诊号码
     * @param status 状态
     * @return
     */

    HospitalizedPatientInfoNew1 getByNoticeIdAndStatus(String noticeId, Integer status);

    /**
     * 根据患者信息
     * @param id   主键
     * @return
     */
    HospitalizedPatientInfoNew1 getHospitalizedPatientInfoById(Integer id);

    /**
     * 根据住院号查找患者信息
     * @param hosNo   住院号
     * @return
     */
    HospitalizedPatientInfoNew1 getHospitalizedPatientInfoByHosNo(String hosNo);

    /**
     * 更新患者信息
     * @param hospitalizedPatientInfo
     */
    void updateHospitalizedPatientInfo(HospitalizedPatientInfoNew1 hospitalizedPatientInfo);

    /**
     * 更新住院通知书
     * @param hospitalizedNotice
     */
    void updateHospitalizedNotice(HospitalizedNoticeNew hospitalizedNotice);

    /**
     * 增加患者入院信息
     * @param hospitalizedPatientInfo
     */
    void createHospitalizedPatientInfo(HospitalizedPatientInfoNew1 hospitalizedPatientInfo);

    /**
     * 从本地获取入院通知书
     * @param noticeId
     * @return
     */
    HospitalizedNoticeNew getHospitalizedNoticeFromLocal(String noticeId);

    /**
     * 患者住院后根据住院获取 基本信息
     * @param inpNo
     * @return
     */
    HospitalizedPatientInfoNew1 getHospitalizedbyInpNo(String inpNo);
}
