package com.alpha.self.diagnosis.pojo.enums;

/**
 * Created by xc.xiong on 2017/9/1.
 * 问题类型
 */
public enum SyAnswerType {

    SUB_ANSWER("1", "子类"),
    PARENT_ANSWER("大类", "大类"),
	SYMTOM("3", "同义词");

    private String value;
    private String text;

    SyAnswerType(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getOrdinal() {
        return this.ordinal();
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

	
}
