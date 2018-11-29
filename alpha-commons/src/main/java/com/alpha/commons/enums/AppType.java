package com.alpha.commons.enums;


public enum AppType {

	ALPHA("alpha", "阿尔法医生"),
	CHILD("child", "儿科"),
	WOMAN("woman", "妇科");
    //PRE("pre", "预问诊");

    private String value;
    private String text;

    AppType(String value, String text) {
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
    
	public static AppType findByValue(String value) {
		for(AppType item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		AppType item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
