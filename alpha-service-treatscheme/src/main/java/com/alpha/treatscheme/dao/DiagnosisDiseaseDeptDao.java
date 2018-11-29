package com.alpha.treatscheme.dao;

import java.util.List;

import com.alpha.treatscheme.pojo.DiagnosisDiseaseDept;


public interface DiagnosisDiseaseDeptDao {


    /**
     * 查询推荐科室列表
     *
     * @param diseaseCode
     * @return
     */
    List<DiagnosisDiseaseDept> listDiagnosisDiseaseDept(String diseaseCode);
}
