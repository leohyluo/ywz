package com.alpha.self.diagnosis.service;

import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;

import java.util.List;

public interface DiagnosisHistoryService {

    /**
     * 病情确认列表（v1.1.0）
     * @param diagnosisId
     * @return
     */
    List<UserDiagnosisDetail> showUserDiagnosisDetail(Long diagnosisId);
}
