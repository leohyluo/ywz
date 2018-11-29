package com.alpha.treatscheme.service;

import java.util.List;

import com.alpha.treatscheme.pojo.DiagnosisDiseaseDept;

public interface DiagnosisDiseaseDeptService {

	/**
     * 查询推荐科室列表
     *
     * @param diseaseCode
     * @return
     */
    List<DiagnosisDiseaseDept> listDiagnosisDiseaseDept(String diseaseCode);

}
