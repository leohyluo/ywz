package com.alpha.commons.enums;

/**
 * 一般症状
 */
public enum GeneralSymptom {

    SHIT(1, "大便相关"),
    URINE(2, "小便相关"),
    APPETITE(3, "食欲相关"),
    MENTALITY(4, "精神状况");

    private Integer value;
    private String text;

    GeneralSymptom(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static GeneralSymptom findByValue(Integer value) {
        for(GeneralSymptom item : values()) {
            if(item.value == value) {
                return item;
            }
        }
        return null;
    }

    public static String getText(Integer value) {
        String text = null;
        GeneralSymptom item = findByValue(value);
        if(item != null) {
            text = item.getText();
        }
        return text;
    }
}
