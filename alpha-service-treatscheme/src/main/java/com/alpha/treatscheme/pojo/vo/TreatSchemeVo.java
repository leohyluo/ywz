package com.alpha.treatscheme.pojo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xc.xiong on 2017/10/12.
 */
public class TreatSchemeVo implements Serializable {
    private static final long serialVersionUID = -1165409203667769212L;

    private String diseaseName;
    private String diseaseCode;
    private String diseaseDefinition;//疾病描述
    private String schemeContent;//治疗意见
    private String schemeCode;//治疗方案编码
    private String schemeName;//治疗方案名称
    private List<Check> checks = new ArrayList<>();//建议检查
    private List<Physicalexam> physicalexams = new ArrayList<>();//建议查体
    private List<DiseaseSign> signs = new ArrayList<>();    //重要体征

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

    public String getDiseaseDefinition() {
        return diseaseDefinition;
    }

    public void setDiseaseDefinition(String diseaseDefinition) {
        this.diseaseDefinition = diseaseDefinition;
    }

    public String getSchemeContent() {
        return schemeContent;
    }

    public void setSchemeContent(String schemeContent) {
        this.schemeContent = schemeContent;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }

    public List<Physicalexam> getPhysicalexams() {
        return physicalexams;
    }

    public void setPhysicalexams(List<Physicalexam> physicalexams) {
        this.physicalexams = physicalexams;
    }

    public List<DiseaseSign> getSigns() {
        return signs;
    }

    public void setSigns(List<DiseaseSign> signs) {
        this.signs = signs;
    }

    public static class TreatmentScheme implements Serializable {
        private static final long serialVersionUID = 1;
    }

    public static class Check implements Serializable {

        private static final long serialVersionUID = -3035168344339110422L;

        private String checkCode;
        private String checkName;

        public String getCheckCode() {
            return checkCode;
        }

        public void setCheckCode(String checkCode) {
            this.checkCode = checkCode;
        }

        public String getCheckName() {
            return checkName;
        }

        public void setCheckName(String checkName) {
            this.checkName = checkName;
        }

    }

    public static class Physicalexam implements Serializable {

        private static final long serialVersionUID = -3195698754242628579L;

        private String examCode;
        private String examName;

        public String getExamCode() {
            return examCode;
        }

        public void setExamCode(String examCode) {
            this.examCode = examCode;
        }

        public String getExamName() {
            return examName;
        }

        public void setExamName(String examName) {
            this.examName = examName;
        }
    }

    public static class DiseaseSign implements Serializable {

        private static final long serialVersionUID = -3195698754242628579L;

        private String signCode;
        private String signName;

        public String getSignCode() {
            return signCode;
        }

        public void setSignCode(String signCode) {
            this.signCode = signCode;
        }

        public String getSignName() {
            return signName;
        }

        public void setSignName(String signName) {
            this.signName = signName;
        }
    }
}
