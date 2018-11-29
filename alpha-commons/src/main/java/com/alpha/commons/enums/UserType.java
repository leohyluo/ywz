package com.alpha.commons.enums;


public enum UserType {

	SELF("1", "自己"),
    OTHER("2", "他人");

    private String value;
    private String text;

    UserType(String value, String text) {
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

	public static UserType findByValue(String value) {
		for(UserType item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
}
