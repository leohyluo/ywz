package com.alpha.treatscheme.dao;

import com.alpha.treatscheme.pojo.DiagnosisDiseasePhysicalexam;

import java.util.List;

/**
 * Created by xc.xiong on 2017/10/12.
 */
public interface DiagnosisDiseasePhysicalexamDao {

    /**
     * 查询建议查体
     *
     * @param diseaseCode
     * @return
     */
    List<DiagnosisDiseasePhysicalexam> listDiagnosisDiseasePhysicalexam(String diseaseCode);
}
