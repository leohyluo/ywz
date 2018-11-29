package com.alpha.self.diagnosis.service;

import java.util.List;

import com.alpha.self.diagnosis.pojo.vo.*;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserInfo;

public interface DiagnosisService {

    BasicQuestionVo start(Long userId, Integer inType, String type, String channel, String pno, Integer diagnosisType, Long inputDiagnosisId);

    /**
     * 问诊结束后展示就诊信息（重构完病历模块可将此删除）
     *
     * @param userId
     * @param diagnosisId
     * @return
     */
    //DiagnosisResultVo showDiagnosisResult(Long userId, Long diagnosisId);

    /**
     * 对接页面展示医生修改后的就诊信息
     *
     * @param userId
     * @param diagnosisId
     * @return
     */
    HisDiagnosisResultVo showHisDiagnosisResult(Long userId, Long diagnosisId);

    /**
     * 更新被导入过的病历数据
     * @param pNo
     */
    void updateMedicineImportAction(String pNo);

    /**
     * 判断病历是否被导入过
     * @param pNo
     * @return
     */
    Integer medicineRecordShowExists(String pNo);

    /**
     * his通过患者身份证号码查看病历
     * @param idcard
     * @param idcard
     * @return
     */
    //DiagnosisResultVo showDiagnosisResult(String idcard);
}
