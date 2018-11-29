package com.alpha.self.diagnosis.service;

import com.alpha.self.diagnosis.pojo.vo.BasicQuestionVo;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.server.rpc.user.pojo.UserInfo;

import java.util.List;

/**
 * Created by xc.xiong on 2017/10/16.
 */
public interface MedicineDiagnosisService {

    /**
     * 诊断方法 主要逻辑
     *
     * @param diagnosisId
     */
    List<UserDiagnosisOutcome> diagnosisOutcome(Long diagnosisId, String mainSympCode,UserInfo userInfo);

    /**
     * 生成诊断结果视图
     * @param diagnosisId
     * @return
     */
    BasicQuestionVo diagnosisOutcomeView(Long diagnosisId);
}
