package com.alpha.self.diagnosis.pojo.vo;

import java.util.Date;

/**
 * 完善个人信息属性
 */
public class BasicInfoVo {

    private Date birth;
    private Integer gender;
    private String weight;

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
