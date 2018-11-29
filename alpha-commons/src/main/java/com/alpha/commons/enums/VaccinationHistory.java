package com.alpha.commons.enums;


public enum VaccinationHistory {

	INTIME(1, "按时接种"),
    UNTIMELY(2, "未按时接种"),
    UNDO(3, "未接种"),
    UNKNOWN(0, "不清楚"),;		

    private Integer value;
    private String text;

    VaccinationHistory(Integer value, String text) {
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
    
	public static VaccinationHistory findByValue(Integer value) {
		for(VaccinationHistory item : values()) {
			if(item.value == value) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(Integer value) {
		String text = null;
		VaccinationHistory item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
