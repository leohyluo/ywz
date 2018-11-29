package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;

import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 */
public interface DiagnosisMainSymptomsDao extends IBaseDao<DiagnosisMainSymptoms, Long> {

    List<DiagnosisMainSymptoms> query(Map<String, Object> param);

    List<DiagnosisMainSymptoms> listByObjectVersion(Integer objectVersion);

    /**
     * 根据主症状名称找主症状
     * @param mainSymptomName
     * @return
     */
    DiagnosisMainSymptoms queryByName(String mainSymptomName);

    List<DiagnosisMainSymptoms> listBySympCodeList(List<String> sympCodeList);

    /**
     * 查询问诊过程中回答了有引申问题的常见伴随症状
     * @param diagnosisId
     * @return
     */
    List<DiagnosisMainSymptoms> listMainSymptomOfExtQuestion(String diagnosisId);

    /**
     * 查询构成主诉的常见伴随症状
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainSymptoms> listConstructSympOfMainSymptom(String mainSympCode, String systemCode);
}
