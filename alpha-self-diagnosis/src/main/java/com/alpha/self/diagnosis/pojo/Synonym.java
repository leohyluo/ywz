package com.alpha.self.diagnosis.pojo;

/**
 * 同义词表
 *
 * @author Administrator
 */
public class Synonym {

    private Long id;

    //同义词
    private String synonym;

    //症状名称
    private String symptomName;

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
