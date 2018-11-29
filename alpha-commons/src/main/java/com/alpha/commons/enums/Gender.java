package com.alpha.commons.enums;

public enum Gender {

	MALE(2), // 男性
	FEMALE(1); // 女性

	private int value;

	private Gender(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static Gender findByType(int value) {
		for (Gender item : values()) {
			if (item.value == value) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + value);
	}
	
	public static String getGenderText(Integer gender) {
		String genderStr = "";
		if(gender == null) {
			genderStr = "暂无";
		} else if(gender == Gender.MALE.getValue()) {
			genderStr = "男";
		} else if(gender == Gender.FEMALE.getValue()) {
			genderStr = "女";
		} else {
			genderStr = "未知";
		}
		return genderStr;
	}
	
}
