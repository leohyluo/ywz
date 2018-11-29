package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisConcomitantSymptom;

import java.util.List;

public interface DiagnosisConcomitantSymptomDao extends IBaseDao<DiagnosisConcomitantSymptom, Long> {

    /**
     * 根据伴随症状名称查询伴随症状字典表
     * @param sympNameList
     * @return
     */
    List<DiagnosisConcomitantSymptom> listBySympName(List<String> sympNameList);
}
