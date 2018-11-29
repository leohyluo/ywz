package com.alpha.commons.enums;

/**
 * 月经选项
 */
public enum MenstruationStatus {

    NORMAL("1", "正常"),
    UNNORMAL("0", "不正常"),
    ABSOLUTE("2", "已绝经");

    private String value;
    private String text;

    MenstruationStatus(String value, String text) {
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
