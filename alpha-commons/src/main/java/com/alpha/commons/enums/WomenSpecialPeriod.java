package com.alpha.commons.enums;

/**
 * Created by xc.xiong on 2017/9/1.
 * 问题类型
 */
public enum WomenSpecialPeriod {

	MENSTRUAL_PERIOD(1, "月经期"),		//月经期
    PREPARE_PREGNANCY(2, "备孕中"),		//备孕中
    IN_PREGNANCY(3, "妊娠期"),			//妊娠期
    LOCTATION_PERIOD(4, "哺乳期");		//哺乳期

    private Integer value;
    private String text;

    WomenSpecialPeriod(Integer value, String text) {
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
    
	public static WomenSpecialPeriod findByValue(Integer value) {
		for(WomenSpecialPeriod item : values()) {
			if(item.value == value) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(Integer value) {
		String text = null;
		WomenSpecialPeriod item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
