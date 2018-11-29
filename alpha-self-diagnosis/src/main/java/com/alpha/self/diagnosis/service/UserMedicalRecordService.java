package com.alpha.self.diagnosis.service;

import com.alpha.server.rpc.diagnosis.pojo.UserMedicalRecord;

import java.util.function.Function;

public interface UserMedicalRecordService {

    /**
     * 生成病历
     * @param diagnosisId
     */
    UserMedicalRecord build(Long diagnosisId, Function<String, String> allergicHistoryFunction);

    /**
     * 获取病历
     * @param diagnosisId
     * @return
     */
    UserMedicalRecord getByDiagnosisId(Long diagnosisId);

    /**
     * 保存病历
     * @param userMedicalRecord
     */
    void save(UserMedicalRecord userMedicalRecord);

    /**
     * 更新
     * @param diD
     */
    void update(Long diD);
}
