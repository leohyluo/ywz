package com.alpha.his.service.etyy;

import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.server.rpc.his.pojo.HisRegisterRecord;

import java.util.List;

/**
 * 挂号业务类
 */
public interface RegisterService {

    /**
     * 获取挂号信息
     * @param
     * @return
     */
    List<RegisterDTO> getUserRegisterInfo(String outPatientNo , String visitTime);

    /**
     * 查询挂号信息
     * @param outPatientNo
     * @param pno
     * @return
     */
    HisRegisterRecord getRegisterRecord(String outPatientNo, String pno);

    /**
     * 医院返回的挂号信息转化为挂号信息实体类
     * @param registerDTOList
     * @return
     */
    List<HisRegisterRecord> registerDTO2HisRegisterRecord(List<RegisterDTO> registerDTOList);

    /**
     * 保存挂号记录
     * @param hisRegisterRecordList
     */
    void saveHisRegisterRecord(List<HisRegisterRecord> hisRegisterRecordList);

    /**
     * 根据门诊号、挂号码从本地获取挂号信息
     * @param outPatientNo
     * @param visitTime
     * @return
     */
    List<HisRegisterRecord> listByOutPatientNoAndVisitTime(String outPatientNo, String visitTime);

    /**
     * 根据门诊号从本地获取挂号信息
     * @param outPatientNo
     * @return
     */
    List<HisRegisterRecord> listByOutPatientNo(String outPatientNo);

    /**
     * 根据挂号码获取挂号信息
     * @param hisRegisterNo
     * @return
     */
    HisRegisterRecord getHisRegisterRecord(String hisRegisterNo);

    Long saveByBatch(List<HisRegisterRecord> hisRegisterRecordList);

    void updateHisRegisterRecord(HisRegisterRecord hisRegisterRecord);
}
