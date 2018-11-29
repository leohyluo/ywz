package com.alpha.commons.enums;

/**
 * Created by HP on 2018/9/30.
 * 导入模块
 */
public enum ImportAndEditeType {
    EDITE_71("71", "mainSymptomName"),
    EDITE_72("72", "presentIllnessHistory"),
    EDITE_73("73", "pastmedicalHistory"),
    EDITE_74("74", "diseases"),
    IMPORT_61("61","mainSymptomName"),
    IMPORT_62("62","presentIllnessHistory"),
    IMPORT_63("63","pastmedicalHistory"),
    IMPORT_64("64","diseases")
   ;

    private String value;
    private String text;

    ImportAndEditeType(String value, String text) {
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

    public static ImportAndEditeType findByValue(String value) {
        for(ImportAndEditeType item : values()) {
            if(item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static String getText(String value) {
        String text = null;
        ImportAndEditeType item = findByValue(value);
        if(item != null) {
            text = item.getText();
        }
        return text;
    }
}
