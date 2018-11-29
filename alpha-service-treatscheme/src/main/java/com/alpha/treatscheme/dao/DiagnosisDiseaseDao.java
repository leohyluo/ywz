package com.alpha.treatscheme.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.commons.core.pojo.DiagnosisDisease;

import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 */
public interface DiagnosisDiseaseDao extends IBaseDao<DiagnosisDisease, Long> {

    /**
     * 查询所有的疾病
     *
     * @param params
     * @return
     */
    List<DiagnosisDisease> listDiagnosisDisease(Map<String, Object> params);

    /**
     * 查询疾病
     *
     * @param diseaseCode
     * @return
     */
    DiagnosisDisease getDiagnosisDisease(String diseaseCode);

    /**
     * 根据用户行为获取热门疾病
     * @return
     */
    List<DiagnosisDisease> listUserHotDisease();
    
    /**
     * 根据系统默认排序查询疾病
     * @param size 要获取的数量
     * @return
     */
    List<DiagnosisDisease> listAnyDiseaseOrderByDefaultOrder(int size);
    
    /**
     * 根据疾病名称查询
     * @param diseaseName
     * @return
     */
    List<DiagnosisDisease> listByDiseaseName(String diseaseName);
}
