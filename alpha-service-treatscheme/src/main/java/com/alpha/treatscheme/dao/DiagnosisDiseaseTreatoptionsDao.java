package com.alpha.treatscheme.dao;

import com.alpha.treatscheme.pojo.DiagnosisDiseaseTreatoptions;

import java.util.List;

/**
 * Created by xc.xiong on 2017/10/12.
 */
public interface DiagnosisDiseaseTreatoptionsDao {

    /**
     * 查询治疗方案
     *
     * @param diseaseCode
     * @return
     */
    List<DiagnosisDiseaseTreatoptions> getDiagnosisDiseaseTreatoptions(String diseaseCode);
}
