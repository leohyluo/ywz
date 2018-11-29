package com.alpha.commons.enums;


public enum GestationalAge {

	LESS_THAN(1, "＜37周"),
    BETWEEN(2, "37周~42周"),
    LARGE_THAN(3, "＞42周");		

    private Integer value;
    private String text;

    GestationalAge(Integer value, String text) {
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
    
	public static GestationalAge findByValue(Integer value) {
		for(GestationalAge item : values()) {
			if(item.value == value) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(Integer value) {
		String text = null;
		GestationalAge item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
