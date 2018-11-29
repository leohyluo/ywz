package com.alpha.self.diagnosis.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "basic_weight_info")
public class BasicWeightInfo implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 性别
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 最小年龄
     */
    @Column(name = "min_age")
    private Double minAge;

    /**
     * 最大年龄
     */
    @Column(name = "max_age")
    private Double maxAge;

    /**
     * 体重正常范围最小值
     */
    @Column(name = "normal_start")
    private Double normalStart;

    /**
     * 体重正常范围最大值
     */
    @Column(name = "normal_end")
    private Double normalEnd;

    /**
     * 默认体重
     */
    @Column(name = "default_weight")
    private Double defaultWeight;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Double getMinAge() {
        return minAge;
    }

    public void setMinAge(Double minAge) {
        this.minAge = minAge;
    }

    public Double getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Double maxAge) {
        this.maxAge = maxAge;
    }

    public Double getNormalStart() {
        return normalStart;
    }

    public void setNormalStart(Double normalStart) {
        this.normalStart = normalStart;
    }

    public Double getNormalEnd() {
        return normalEnd;
    }

    public void setNormalEnd(Double normalEnd) {
        this.normalEnd = normalEnd;
    }

    public Double getDefaultWeight() {
        return defaultWeight;
    }

    public void setDefaultWeight(Double defaultWeight) {
        this.defaultWeight = defaultWeight;
    }

}