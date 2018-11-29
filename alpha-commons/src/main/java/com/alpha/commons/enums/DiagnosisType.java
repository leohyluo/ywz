package com.alpha.commons.enums;

/**
 * 预问诊类型
 */
public enum DiagnosisType {

	FIRST_DIAGNOSIS(1, "初诊"),
	MULTIPLE_DIAGNOSIS(2, "复诊");

    private Integer value;
    private String text;

    DiagnosisType(Integer value, String text) {
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
    
	public static DiagnosisType findByValue(String value) {
		for(DiagnosisType item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		DiagnosisType item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
