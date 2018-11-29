package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisQuestionAnswer;
import com.alpha.server.rpc.diagnosis.pojo.vo.MedicineQuestionVo;

import java.util.Collection;
import java.util.List;

/**
 * Created by xc.xiong on 2017/9/5.
 */
public interface DiagnosisQuestionAnswerDao extends IBaseDao<DiagnosisQuestionAnswer, Long> {

    /**
     * 根据问题编号查询所有的答案
     *
     * @param questionCodes
     * @return
     */
    List<DiagnosisQuestionAnswer> listDiagnosisQuestionAnswer(String mainSympCode, Collection<String> questionCodes);


    /**
     * 根据答案编号查询答案
     *
     * @param questionCode
     * @param answerCode
     * @return
     */
    DiagnosisQuestionAnswer getDiagnosisQuestionAnswer(String questionCode, String answerCode);


    /**
     * 根据主症状编码、问题编码、答案编码找出有效的疾病（非隐藏、非删除）
     * @param answerCodes
     * @return
     */
    List<DiagnosisQuestionAnswer> listDiagnosisQuestionAnswer(String mainSympCode, String questionCode, Collection<String> answerCodes, Collection<String> hiddenAnswerCodes);

    /**
     * v1.1.3
     * 根据主症状编码、问题编码、答案编码找出有效的疾病（非隐藏、非删除）
     * @param answerCodes
     * @return
     */
    List<DiagnosisQuestionAnswer> listByAnswerCodes(String mainSympCode, String questionCode, Collection<String> answerCodes, Collection<String> hiddenAnswerCodes);

    /**
     * 根据问题、答案编号查询有效的疾病
     *
     * @param answerCodes
     * @param questionCodes
     * @return
     */
    List<MedicineQuestionVo> listMedicineQuestionVo2(String mainSympCode, Collection<String> questionCodes, Collection<String> answerCodes);
    
    /**
     * 查询问题下的所有隐藏答案
     * @param questionCode
     * @return
     */
    List<DiagnosisQuestionAnswer> listHiddenAnswers(String questionCode);
}
