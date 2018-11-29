package com.alpha.commons.enums;


public enum ChildrenType {

	NEWER("newer", "新生儿"),
    COMMON("common", "普通患儿");

    private String value;
    private String text;

    ChildrenType(String value, String text) {
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
    
	public static ChildrenType findByValue(String value) {
		for(ChildrenType item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		ChildrenType item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
