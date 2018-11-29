package com.alpha.commons.enums;

/**
 * Created by HP on 2018/9/30.
 * 医院标识 按照对接顺序编排即可
 */
public enum HospitalCode {
    EYyy("A001","深圳市儿童医院"),
    GMyy("A002","深圳市光明新区人民医院"),
    YFyy("A003","南京市逸夫医院"),
    BJWOMAN("A004","北京妇科医院");

    HospitalCode(String code, String value) {
        this.value = value;
        this.code = code;
    }

    private String code;
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static HospitalCode findByValue(String code) {
        for(HospitalCode item : values()) {
            if(item.code.equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static String getText(String code) {
        String text = null;
        HospitalCode item = findByValue(code);
        if(item != null) {
            text = item.getValue();
        }
        return text;
    }
}
