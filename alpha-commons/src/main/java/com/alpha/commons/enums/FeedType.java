package com.alpha.commons.enums;


public enum FeedType {

	BREAST_FEED(1, "母乳喂养"),
    MANUAL_FEED(2, "人工喂养"),
    MIXTURE_FEED(3, "混合喂养");		

    private Integer value;
    private String text;

    FeedType(Integer value, String text) {
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
    
	public static FeedType findByValue(Integer value) {
		for(FeedType item : values()) {
			if(item.value == value) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(Integer value) {
		String text = null;
		FeedType item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
