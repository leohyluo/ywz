package com.alpha.self.diagnosis.pojo.enums;

public enum ConstructMainSympType {

    病程("1"),
    次数("2");

    private String code;

    ConstructMainSympType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
