package com.alpha.server.rpc.diagnosis.pojo;

public class DiagnosisDepartment {


    /**
     *
     */
    private Long id;

    /**
     * 科室编码
     */
    private String deptCode;

    /**
     * 一级科室名称
     */
    private String level1;

    /**
     * 二级科室名称
     */
    private String level2;


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


    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel1() {
        return this.level1;
    }


    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getLevel2() {
        return this.level2;
    }


}
