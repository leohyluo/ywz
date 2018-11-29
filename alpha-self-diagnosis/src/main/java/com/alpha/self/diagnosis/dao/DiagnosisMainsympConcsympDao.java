package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp;
import com.alpha.server.rpc.diagnosis.pojo.vo.MedicineQuestionVo;

import java.util.Collection;
import java.util.List;

/**
 * Created by xc.xiong on 2017/9/5.
 */
public interface DiagnosisMainsympConcsympDao extends IBaseDao<DiagnosisMainsympConcsymp, Long> {


    /**
     * 查询主症状常见伴随症状
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainsympConcsymp> listCommonSymp(String mainSympCode);
    /**
     * 查询伴随症状，疾病关系
     *
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainsympConcsymp> listDiagnosisMainsympConcsymp(String mainSympCode);

    /**
     * 查询伴随症状，疾病关系
     *
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainsympConcsymp> listCommonDiagnosisMainsympConcsymp(String mainSympCode);

    /**
     * 查询伴随症状
     *
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainsympConcsymp> listConcsymp(String mainSympCode);

    /**
     * 查询伴随症状
     *
     * @param mainSympCode
     * @param concSympCodes
     * @return
     */
    List<DiagnosisMainsympConcsymp> listDiagnosisMainsympConcsymp(String mainSympCode, Collection concSympCodes);

    /**
     * 根据伴随症状编码查询
     * @param concSympCodes
     * @return
     */
    List<DiagnosisMainsympConcsymp> listByConcSympCodes(Collection concSympCodes);

    /**
     * 根据伴随症状名字查询
     * @param mainSympCode
     * @param concSympNames
     * @return
     */
    List<DiagnosisMainsympConcsymp> listByConcSympNames(String mainSympCode, Collection concSympNames);

    /**
     * 查询伴随症状
     *
     * @param mainSympCode
     * @param concSympCodes
     * @return
     */
    List<MedicineQuestionVo> listDiagnosisMainsympConcsymp(String mainSympCode, List<String> concSympCodes);

    /**
     * 查询主症状下权重最高的伴随症状
     * @param mainSympCode
     * @return
     */
    DiagnosisMainsympConcsymp getMaxWeightConcSymp(String mainSympCode);

    /**
     * 获取伴随症状与疾病的关系
     * @param mainSympCode
     * @param concSympCodes
     * @return
     */
    List<DiagnosisMainsympConcsymp> listByMainSymptomAndconcSympCodes(String mainSympCode, List<String> concSympCodes);
}
