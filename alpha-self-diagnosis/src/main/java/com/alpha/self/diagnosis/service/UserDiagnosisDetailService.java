package com.alpha.self.diagnosis.service;

import com.alpha.self.diagnosis.pojo.vo.BasicQuestionVo;
import com.alpha.self.diagnosis.pojo.vo.ReplyQuestionVo;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.user.pojo.UserInfo;

public interface UserDiagnosisDetailService {

    /**
     * 生成一条用户问诊过程
     * @param questionVo
     */
    void addUserDiagnosisDetail(BasicQuestionVo questionVo, UserInfo userInfo);

    /**
     * 更新用户问诊过程
     * @param questionVo
     * @param userInfo
     * @param diagnosisQuestion
     */
    void updateUserDiagnosisDetail(ReplyQuestionVo questionVo, UserInfo userInfo, DiagnosisMainsympQuestion diagnosisQuestion);
}
