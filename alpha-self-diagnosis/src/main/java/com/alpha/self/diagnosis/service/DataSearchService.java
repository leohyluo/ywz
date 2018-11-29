package com.alpha.self.diagnosis.service;

import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.SearchKeys;
import com.alpha.server.rpc.user.pojo.UserInfo;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * 数据查询接口
 */
public interface DataSearchService {

    /**
     * 既往史搜索
     * @param userInfo
     * @param keyword
     * @return
     */
    List<IAnswerVo> searchPastMedicalHistory(UserInfo userInfo, String keyword);

    /**
     * 过敏史搜索
     * @param userInfo
     * @param keyword
     * @return
     */
    List<IAnswerVo> searchAllergyHistory(UserInfo userInfo, String keyword);

    /**
     * 主症状搜索
     * @param searchKeys
     * @return
     */
    DiagnosisMainSymptoms searchMainSymptom(SearchKeys searchKeys);

    /**
     * 伴随症状搜索
     * @param diagnosisId
     * @param mainSympCode
     * @param userInfo
     * @param keyword
     * @return
     */
    LinkedHashSet<IAnswerVo> searchCocnSymptom(Long diagnosisId, String mainSympCode, UserInfo userInfo, String keyword);
}
