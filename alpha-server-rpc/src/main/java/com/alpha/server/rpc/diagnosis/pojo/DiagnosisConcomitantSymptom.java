package com.alpha.server.rpc.diagnosis.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "diagnosis_concomitant_symptom")
public class DiagnosisConcomitantSymptom  implements Serializable {


    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 伴随症状编码
     */
    @Column(name = "symp_code")
    private String sympCode;

    /**
     * 伴随症状名称
     */
    @Column(name = "symp_name")
    private String sympName;

    /**
     * 通俗化名称
     */
    @Column(name = "popu_name")
    private String popuName;

    /**
     * 拼音助记符
     */
    @Column(name = "symbol")
    private String symbol;

    /**
     * 性别
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 最小年龄
     */
    private Integer minAge;

    /**
     * 最大年龄
     */
    private Integer maxAge;

    /**
     * 特殊时期
     */
    private Integer specialPeriod;
    
    /**
     * 客户端显示类型
     */
    private String displayType;

    //一般症状（1：大便 2：小便 3：食欲 4：精神）
    @Column(name = "general_symptom")
    private Integer generalSymptom;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setSympCode(String sympCode) {
        this.sympCode = sympCode;
    }

    public String getSympCode() {
        return this.sympCode;
    }


    public void setSympName(String sympName) {
        this.sympName = sympName;
    }

    public String getSympName() {
        return this.sympName;
    }


    public void setPopuName(String popuName) {
        this.popuName = popuName;
    }

    public String getPopuName() {
        return this.popuName;
    }


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }


    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGender() {
        return this.gender;
    }


    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMinAge() {
        return this.minAge;
    }


    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMaxAge() {
        return this.maxAge;
    }


    public void setSpecialPeriod(Integer specialPeriod) {
        this.specialPeriod = specialPeriod;
    }

    public Integer getSpecialPeriod() {
        return this.specialPeriod;
    }

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

    public Integer getGeneralSymptom() {
        return generalSymptom;
    }

    public void setGeneralSymptom(Integer generalSymptom) {
        this.generalSymptom = generalSymptom;
    }
}
