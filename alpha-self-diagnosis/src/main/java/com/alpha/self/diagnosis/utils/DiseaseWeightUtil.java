package com.alpha.self.diagnosis.utils;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.diagnosis.pojo.vo.MedicineQuestionVo;

import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/10/16.
 */
public class DiseaseWeightUtil {

    /**
     * 计算伴随症状权重
     * 症状权重=问题 * 伴随症状+伴随症状
     *
     * @param mqv
     */
    public static Double diagnosisOutcome(int questionSize, StringBuffer calculationFormula, MedicineQuestionVo mqv, Map<String, List<MedicineQuestionVo>> dmcsMap, DiagnosisMainsympQuestion dmQuestion) {
        try {
            if (dmcsMap == null || dmcsMap.size() == 0) {
                return 0d;
            }
            List<MedicineQuestionVo> dmcs = dmcsMap.get(mqv.getDiseaseCode());
            if (dmcs == null || dmcs.size() == 0) {
                return 0d;
            }
            Double N = 0d;//答案
            Double Y = questionWeightFormula(questionSize, mqv.getQuestionWeight(), mqv.getQuestionStandardDeviation(), calculationFormula); //问题
            for (MedicineQuestionVo mdc : dmcs) {
                N = N + answerWeightFormula(mdc.getAnswerWeight(), mqv.getAnswerStandardDeviation(), dmQuestion, calculationFormula);

            }
            return diseaseWeightFormula(Y, N, calculationFormula);
        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }
    
    /**
     * 计算伴随症状权重
     * 症状权重=问题 * 伴随症状+伴随症状
     *
     * @param
     */
    public static Double diagnosisOutcome2(int questionSize, String diseaseName,  StringBuffer calculationFormula, List<MedicineQuestionVo>  mqvs, DiagnosisMainsympQuestion dmQuestion) {
        try {
            MedicineQuestionVo mqv = null;
            //Y伴随症状问题权重
            Double Y = 0.0;
            Double N = 0d;//答案
            for (MedicineQuestionVo mdc : mqvs) {
                calculationFormula.append("<p>" + diseaseName + " " + mdc.getDiseaseCode());
                calculationFormula.append(" >> " + mdc.getQuestionTitle() + " " + mdc.getQuestionCode());
                calculationFormula.append(" $" + mdc.getAnswerTitle() + " " + mdc.getAnswerCode() + "</p>");
                if(mqv == null) {
                    mqv = mdc;
                    Double Y1 = questionWeightFormula(questionSize, mqv.getQuestionWeight(), mqv.getQuestionStandardDeviation(), calculationFormula); //问题
                    if (Y == 0.0) {
                        Y = Y1;
                    }
                }
                Double answerWeight = mdc.getAnswerWeight();
                N = N + answerWeightFormula(answerWeight, mqv.getAnswerStandardDeviation(), dmQuestion, calculationFormula);
            }
            return diseaseWeightFormula(Y, N, calculationFormula);
        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }

    /**
     * 计算单个伴随症状权重
     * 症状权重=问题 * 关键伴随症状
     *
     * @param
     */
    public static Double calcSingleConcSympWeight(int questionSize, String diseaseName, StringBuffer calculationFormula, List<MedicineQuestionVo>  mqvs, DiagnosisMainsympQuestion dmQuestion) {
        try {
            MedicineQuestionVo mqv = null;
            //Y伴随症状问题权重
            Double Y = 0.0;
            Double N = 0d;//答案
            for (MedicineQuestionVo mdc : mqvs) {
                calculationFormula.append("<p>" + diseaseName + " " + mdc.getDiseaseCode());
                calculationFormula.append(" >> " + mdc.getQuestionTitle() + " " + mdc.getQuestionCode());
                calculationFormula.append(" $" + mdc.getAnswerTitle() + " " + mdc.getAnswerCode() + "</p>");
                if(mqv == null) {
                    mqv = mdc;
                    Double Y1 = questionWeightFormula(questionSize, mqv.getQuestionWeight(), mqv.getQuestionStandardDeviation(), calculationFormula); //问题
                    if (Y == 0.0) {
                        Y = Y1;
                    }
                }
                Double answerWeight = mdc.getAnswerWeight();
                N = answerWeightFormula(answerWeight, mqv.getAnswerStandardDeviation(), dmQuestion, calculationFormula);
                calculationFormula.append("</br>");
                calculationFormula.append("Weight: " + (N * Y) + " = " + N + " * " + Y);
                N = N * Y;
            }
            return N;
        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }

    /**
     * 计算伴随症状权重
     * 症状权重=问题 * 伴随症状+伴随症状
     *
     * @param mqv
     */
    public static Double diagnosisOutcome_bak(int questionSize, StringBuffer calculationFormula, MedicineQuestionVo mqv, Map<String, List<DiagnosisMainsympConcsymp>> dmcsMap, DiagnosisMainsympQuestion dmQuestion) {
        try {
            if (dmcsMap == null || dmcsMap.size() == 0) {
                return 0d;
            }
            List<DiagnosisMainsympConcsymp> dmcs = dmcsMap.get(mqv.getDiseaseCode());
            if (dmcs == null || dmcs.size() == 0) {
                return 0d;
            }
            Double N = 0d;//答案
            Double Y = questionWeightFormula(questionSize, mqv.getQuestionWeight(), mqv.getQuestionStandardDeviation(), calculationFormula); //问题
            for (DiagnosisMainsympConcsymp mdc : dmcs) {
                N = N + answerWeightFormula(mdc.getRate(), mqv.getAnswerStandardDeviation(), dmQuestion, calculationFormula);

            }
            return diseaseWeightFormula(Y, N, calculationFormula);
        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }

    /**
     * 假设问题总数为N
     * 公式   X=（100-N*（N+1）/2 *标准差）/(N+1)
     * 每个问题的权重  Y=    X+（问题权重排序-1）*标准差
     *
     * @param questionSize
     * @param questionWeight
     * @param questionStandardDeviation
     * @param calculationFormula
     * @return
     */
    public static Double questionWeightFormula(int questionSize, Double questionWeight, Double questionStandardDeviation, StringBuffer calculationFormula) {
        try {
            //排序在最后的问题的权重 X=（100-N*（N+1）/2 *标准差）/(N+1)
            Double X = (100 - questionSize * (questionSize + 1) / 2 * questionStandardDeviation) / (questionSize + 1);
            calculationFormula.append("Double " + X + " = (100 - " + questionSize + " * (" + questionSize + " + 1) / 2 * " + questionStandardDeviation + ") / (" + questionSize + " + 1)");
            //每个问题的权重 Y=    X+（问题权重排序-1）*标准差
            Double Y = X + (questionWeight - 1) * questionStandardDeviation;
            calculationFormula.append("</br>");
            calculationFormula.append("Double " + Y + " = " + X + " + (" + questionWeight + " - 1) * " + questionStandardDeviation + "");
            return Y;
        } catch (Exception e) {
            return 0d;
        }
    }

    /**
     * 答案权重等于
     * 假设主症状下疾病答案最多的答案总数总数为N
     * 公式   X=（100-N*（N+1）/2 *标准差）/(N+1)
     *
     * @param answerWeight
     * @param answerStandardDeviation
     * @param dmQuestion
     * @param calculationFormula
     * @return
     */
    public static Double answerWeightFormula(Double answerWeight, Double answerStandardDeviation, DiagnosisMainsympQuestion dmQuestion, StringBuffer calculationFormula) {
        try {
        	answerWeight = answerWeight == null ? 0.0d : answerWeight;
            //排序在最后的答案的权重  X=（100-N*（N+1）/2 *标准差）/(N+1)
            Double M = (100 - dmQuestion.getAnswerTotal() * answerStandardDeviation / (dmQuestion.getAnswerTotal() + 1));
            calculationFormula.append("</br>");
            calculationFormula.append("Double " + M + " = (100 - " + dmQuestion.getAnswerTotal() + " * " + answerStandardDeviation + " / (" + dmQuestion.getAnswerTotal() + " + 1));");
            //每个答案的权重   N=M+（答案权重排序-1）*标准差
            Double N = M + (answerWeight - 1) * answerStandardDeviation;
            calculationFormula.append("</br>");
            calculationFormula.append("Double " + N + " = " + M + " + (" + answerWeight + " - 1) * " + answerStandardDeviation + "");
            //疾病的权重每个问题和答案的乘积求和。
            return N;
        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }

    /**
     * 疾病权重等于 问题权重乘以答案权重
     *
     * @param Y
     * @param N
     * @param calculationFormula
     * @return
     */
    public static Double diseaseWeightFormula(Double Y, Double N, StringBuffer calculationFormula) {
        try {
            double X = N * Y;
            calculationFormula.append("</br>");
            calculationFormula.append(" Weight: " + X + " =  " + N + " * " + Y);
            return X;
        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }

}
