package com.alpha.self.diagnosis.service;

import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.user.pojo.UserInfo;

import java.util.List;

/**
 * Created by xc.xiong on 2017/10/10.
 */
public interface MedicineAnswerAutoService {
    /**
     * 自动计算医学问题
     *
     * @param diagnosisId
     * @param mainSympCode
     * @param userInfo
     */
    void autoCalculateAnswer(Long diagnosisId, String mainSympCode, UserInfo userInfo);

    /**
     * 获取所有的自动计算问题
     *
     * @param mainSympCode
     * @param userInfo
     * @return
     */
    List<DiagnosisMainsympQuestion> listAutoQuestion(String mainSympCode, UserInfo userInfo);
}
