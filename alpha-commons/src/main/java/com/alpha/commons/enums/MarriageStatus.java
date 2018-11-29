package com.alpha.commons.enums;

/**
 * 婚姻状况
 */
public enum MarriageStatus {

    MARRIED("1", "已婚"),
    UNMARRIED("0", "未婚");

    private String value;
    private String text;

    MarriageStatus(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
