package com.alpha.commons.enums;


public enum APICode {

	GET_USERINFO("1", "获取用户信息"),
    GET_DIAGNOSISINFO("2", "获取就诊信息");

    private String value;
    private String text;

    APICode(String value, String text) {
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
    
	public static APICode findByValue(String value) {
		for(APICode item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		APICode item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
