package com.alpha.commons.enums;


public enum InType {

	HIS(1, "医院"),
    WECHAR(2, "微信"),
    ALPHA(3, "阿尔法");

    private int value;
    private String text;

    InType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
    
	public static InType findByValue(int value) {
		for(InType item : values()) {
			if(item.value == value) {
				return item;
			}
		}
		return null;
	}
	
}
