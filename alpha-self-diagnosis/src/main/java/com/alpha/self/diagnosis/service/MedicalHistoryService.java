package com.alpha.self.diagnosis.service;

import com.alpha.server.rpc.diagnosis.pojo.DiagnosisPastmedicalHistory;

import java.util.List;

/**
 * 病史接口
 */
public interface MedicalHistoryService {

    /**
     * 查询主症状下的既往史
     * @param mainSympCode
     * @return
     */
    List<DiagnosisPastmedicalHistory> listPastMedicalHistoryByMainSympCode(String mainSympCode);

    /**
     * 根据主症状与疾病编码查询既往史
     * @param mainSympCode
     * @param systemCode
     * @return
     */
    List<DiagnosisPastmedicalHistory> listPastmedicalHistoryByMainSympCodeAndSystemCode(String mainSympCode, String systemCode);
}
