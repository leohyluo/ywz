package com.alpha.server.rpc.diagnosis.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "hospital_dept")
public class HospitalDept implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "hospital_code")
    private String hospitalCode;

    @Column(name = "dept_code")
    private String deptCode;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "is_open")
    private Integer isOpen;

    @Column(name = "object_version")
    private Integer objectVersion;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "max_age")
    private Integer maxAge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getObjectVersion() {
        return objectVersion;
    }

    public void setObjectVersion(Integer objectVersion) {
        this.objectVersion = objectVersion;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
}
