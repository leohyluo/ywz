package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;

import java.util.List;

/**
 * Created by xc.xiong on 2017/9/5.
 */
public interface DiagnosisMainsympQuestionDao extends IBaseDao<DiagnosisMainsympQuestion, Long> {

    /**
     * 查询主症状下的所有问题
     *
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainsympQuestion> listDiagnosisMainsympQuestion(String mainSympCode);

    List<DiagnosisMainsympQuestion> listDiagnosisMainsympQuestionAll();

    /**
     * 查询主症状的未回答问题
     *
     * @param mainSympCode
     * @param defaultOrder
     * @return
     */
    List<DiagnosisMainsympQuestion> listNextAllQuestion(String mainSympCode, int defaultOrder);

    /**
     * 查询主症状的自动计算问题
     *
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainsympQuestion> listAutoQuestion(String mainSympCode);

    /**
     * 查询主症状的下一个问题
     *
     * @param mainSympCode
     * @param defaultOrder
     * @return
     */
    DiagnosisMainsympQuestion getNextQuestion(String mainSympCode, int defaultOrder);


    /**
     * 查询主症状的单个问题
     *
     * @param questionCode
     * @return
     */
    DiagnosisMainsympQuestion getDiagnosisMainsympQuestion(String questionCode, String mainSympCode);

    /**
     * 查询主症状的未回答问题
     *
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainsympQuestion> listDiseaseQuestion(String mainSympCode);

    /**
     * 查询主症状下疾病下的所有问题，并计算答案数量
     *
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainsympQuestion> listAnswerCount(String mainSympCode);
    
    /**
     * 查询主症状下疾病下的所有伴随症状，并计算伴随症状数量
     *
     * @param mainSympCode
     * @return
     */
    List<DiagnosisMainsympQuestion> listConcSymptomCount(String mainSympCode);
}
