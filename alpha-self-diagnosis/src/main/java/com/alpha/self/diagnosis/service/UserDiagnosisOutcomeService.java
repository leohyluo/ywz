package com.alpha.self.diagnosis.service;

import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;

/**
 * Created by xc.xiong on 2017/10/12.
 */
public interface UserDiagnosisOutcomeService {

    /**
     * 获取前5诊断疾病
     *
     * @param diagnosisId
     * @return
     */
    List<UserDiagnosisOutcome> listTop5UserDiagnosisOutcome(Long diagnosisId, String sympCode);

    /**
     * 确认诊断结果
     */
    boolean confirmODiagnosisOutcome(Long diagnosisId, String diseaseCode);

}
