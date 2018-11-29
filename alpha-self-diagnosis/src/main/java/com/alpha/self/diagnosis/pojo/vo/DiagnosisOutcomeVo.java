package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xc.xiong on 2017/9/14.
 */
public class DiagnosisOutcomeVo implements Serializable, IAnswerVo {

    private static final long serialVersionUID = 2343102850287212637L;

    /**
     *
     */
    private Long id;

    /**
     * 诊断号
     */
    private Long diagnosisId;

    /**
     * 疾病名称
     */
    private String diseaseName;

    /**
     * 疾病编码
     */
    private String diseaseCode;

    /**
     * 描述
     */
    private String description;
    /**
     * 特异性属性
     */
    private Integer answerSpec;

    /**
     * 发病概率
     */
    private Double probability = 0.5;

    /**
     * 计算出来的权重
     */
    private Double weight;


    /**
     * 治疗建议
     */
    private String suggest;

    public DiagnosisOutcomeVo() {

    }

    public DiagnosisOutcomeVo(Long id, Long diagnosisId, String diseaseName, String diseaseCode, Integer answerSpec, Double probability, Double weight, String suggest) {
        this.id = id;
        this.diagnosisId = diagnosisId;
        this.diseaseName = diseaseName;
        this.diseaseCode = diseaseCode;
        this.answerSpec = answerSpec;
        this.probability = probability;
        this.weight = weight;
        this.suggest = suggest;
    }

    public static List<IAnswerVo> convertDiagnosisOutcomeVo(List<UserDiagnosisOutcome> udos) {
        List<IAnswerVo> outcomeVos = new ArrayList<>();
        for (UserDiagnosisOutcome udo : udos) {
            DiagnosisOutcomeVo outcomeVo = new DiagnosisOutcomeVo(udo);
            outcomeVos.add(outcomeVo);
        }
        return outcomeVos;
    }

    public DiagnosisOutcomeVo(UserDiagnosisOutcome udo) {
        this.id = udo.getId();
        this.diagnosisId = udo.getDiagnosisId();
        this.diseaseName = udo.getDiseaseName();
        this.diseaseCode = udo.getDiseaseCode();
        this.answerSpec = udo.getAnswerSpec();
        this.probability = udo.getProbability();
        this.description = udo.getDescription();
        this.weight = udo.getWeight();
        this.suggest = udo.getSuggest();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public Integer getAnswerSpec() {
        return answerSpec;
    }

    public void setAnswerSpec(Integer answerSpec) {
        this.answerSpec = answerSpec;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
