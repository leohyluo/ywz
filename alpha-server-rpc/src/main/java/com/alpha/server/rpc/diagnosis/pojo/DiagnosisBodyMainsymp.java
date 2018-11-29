package com.alpha.server.rpc.diagnosis.pojo;

public class DiagnosisBodyMainsymp {


    /**
     * id
     */
    private Long id;

    /**
     * 主症状编码
     */
    private String mainSympCode;

    /**
     * 部位编码
     */
    private String bodyCode;


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


    public void setBodyCode(String bodyCode) {
        this.bodyCode = bodyCode;
    }

    public String getBodyCode() {
        return this.bodyCode;
    }


}
