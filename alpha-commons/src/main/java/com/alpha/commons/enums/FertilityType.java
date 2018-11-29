package com.alpha.commons.enums;


public enum FertilityType {

	NATURAL_BIRTH(1, "顺产"),
    CESAREAN_BIRTH(2, "剖腹产"),
    FORCEPS_MIDWIFERY(3, "产钳助产");		

    private Integer value;
    private String text;

    FertilityType(Integer value, String text) {
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
    
	public static FertilityType findByValue(Integer value) {
		for(FertilityType item : values()) {
			if(item.value == value) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(Integer value) {
		String text = null;
		FertilityType item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
