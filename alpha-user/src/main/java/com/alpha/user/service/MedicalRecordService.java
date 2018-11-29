package com.alpha.user.service;

import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.user.pojo.DiagnosisMedicalTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/10/12.
 */
public interface MedicalRecordService {

    /**
     * 获取当前问诊过程内容
     *
     * @param
     * @param templateCode
     */
    void getMedicalRecord(String templateCode, List<UserDiagnosisDetail> udds, DiagnosisMainSymptoms symptom, UserBasicRecord record);


    String getMaminSymptomName(DiagnosisMedicalTemplate diagnosisMedicalTemplate, Map<String, String> question, DiagnosisMainSymptoms symptom);

    String getPresentIllnessHistory(DiagnosisMedicalTemplate diagnosisMedicalTemplate, Map<String, String> question, DiagnosisMainSymptoms symptom, String unSelectedCommonConcSymp, String generalSymptomText);

    String getPresentIllnessHistoryHospital(DiagnosisMedicalTemplate diagnosisMedicalTemplate, UserBasicRecord record);


}
