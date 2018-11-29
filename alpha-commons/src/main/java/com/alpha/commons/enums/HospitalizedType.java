package com.alpha.commons.enums;


public enum HospitalizedType {

	NONE("1", "未住过院"),
    CONFIRM("10", "已签名确认");

    private String value;
    private String text;

    HospitalizedType(String value, String text) {
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
    
	public static HospitalizedType findByValue(String value) {
		for(HospitalizedType item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		HospitalizedType item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
