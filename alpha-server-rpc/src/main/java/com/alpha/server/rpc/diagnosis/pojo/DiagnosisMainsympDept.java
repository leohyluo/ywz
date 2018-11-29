package com.alpha.server.rpc.diagnosis.pojo;

public class DiagnosisMainsympDept {


    /**
     * id
     */
    private Long id;

    /**
     * 科室编码
     */
    private String deptCode;

    /**
     * 主症状编码
     */
    private String mainSympCode;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptCode() {
        return this.deptCode;
    }


    public void setMainSympCode(String mainSympCode) {
        this.mainSympCode = mainSympCode;
    }

    public String getMainSympCode() {
        return this.mainSympCode;
    }


}
