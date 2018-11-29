package com.alpha.commons.enums;


public enum Event {

	SUBSCRIBE("subscribe", "关注"),
	UNSUBSCRIBE("unsubscribe", "取消关注"),
	SCAN("SCAN", "已关注");

    private String value;
    private String text;

    Event(String value, String text) {
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
    
	public static Event findByValue(String value) {
		for(Event item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		Event item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
