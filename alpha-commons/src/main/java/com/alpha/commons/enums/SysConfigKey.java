package com.alpha.commons.enums;

public enum SysConfigKey {

	USEFUL("useful");			//点赞
	
	private String value;
	
	private SysConfigKey(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public static SysConfigKey findByType(String value) {
		for(SysConfigKey item : values()) {
			if(item.value == value) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + value);
	}
}
