package com.alpha.commons.enums;


public enum RegisterType {

	LIVE("1", "现场挂号"),
	APPOINTMENT("2", "线上预约"),
    GET_FOR_APPOINTMENT("3", "线上预约后到现场取号");

    private String value;
    private String text;

    RegisterType(String value, String text) {
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
    
	public static RegisterType findByValue(String value) {
		for(RegisterType item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		RegisterType item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}

	public static Boolean isLive(Integer type) {
    	String typeStr = String.valueOf(type);
		if(typeStr.equals(RegisterType.LIVE.getValue()) || typeStr.equals(RegisterType.GET_FOR_APPOINTMENT.getValue())) {
			return true;
		}
		return false;
	}

}
