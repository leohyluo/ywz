package com.alpha.treatscheme.dao;

import com.alpha.treatscheme.pojo.DiagnosisDiseaseCheck;

import java.util.List;

/**
 * Created by xc.xiong on 2017/10/12.
 */
public interface DiagnosisDiseaseCheckDao {


    /**
     * 查询建议检查
     *
     * @param diseaseCode
     * @return
     */
    List<DiagnosisDiseaseCheck> listDiagnosisDiseaseCheck(String diseaseCode);
}
