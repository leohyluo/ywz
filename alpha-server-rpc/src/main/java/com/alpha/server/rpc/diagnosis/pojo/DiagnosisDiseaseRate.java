package com.alpha.server.rpc.diagnosis.pojo;

public class DiagnosisDiseaseRate {


    /**
     * id
     */
    private Long id;

    /**
     * 主症状编码
     */
    private String mainSympCode;

    /**
     * 疾病编码
     */
    private String diseaseCode;

    /**
     * 发病概率
     */
    private Double rate;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setMainSympCode(String mainSympCode) {
        this.mainSympCode = mainSympCode;
    }

    public String getMainSympCode() {
        return this.mainSympCode;
    }


    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseCode() {
        return this.diseaseCode;
    }


    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return this.rate;
    }


}
