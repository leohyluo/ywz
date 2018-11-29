package com.alpha.commons.enums;


public enum LiverRenalFunc {

	NORMAL(1, "正常"),
    UNNORMAL(2, "不正常"),
    UNKNOWN(3, "不清楚");		

    private Integer value;
    private String text;

    LiverRenalFunc(Integer value, String text) {
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
    
	public static LiverRenalFunc findByValue(Integer value) {
		for(LiverRenalFunc item : values()) {
			if(item.value == value) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(Integer value) {
		String text = null;
		LiverRenalFunc item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
