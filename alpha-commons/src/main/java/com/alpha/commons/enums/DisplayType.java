package com.alpha.commons.enums;


public enum DisplayType {

	MAINSYMPTOM_INPUT("mainsymptom_input", "主诉输入"),
    RADIO_MAINSYMP("radio_mainsymp", "主诉选择"),
    RADIO_MORE_INPUT_CONFIRM("radio_more_input_confirm", "预问诊主诉选择"),
    RADIO("radio", "医学单选"),
    CHECKBOX("checkbox", "医学多选"),
    DAYS("days", "主诉时间"),
    NUM_OF_TIMES("times","次数"),
    CHECKBOX_MORE_INPUT_CONFIRM("checkbox_more_input_confirm", "伴随症状"),
    TEMPERATURE("temperature", "体温");

    private String value;
    private String text;

    DisplayType(String value, String text) {
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
    
	public static DisplayType findByValue(String value) {
		for(DisplayType item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		DisplayType item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
