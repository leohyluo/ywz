package com.alpha.commons.enums;

import com.alpha.commons.util.StringUtils;

public enum DiagnosisStatus {

	REGISTERED("1", "已挂号"),
	UN_ACTIVE("2", "未排队"),
	ACTIVED("3", "已激活排队信息"),
	IN_QUEUE("4", "排队中"),
    PRE_DIAGNOSIS_FINISH("10", "预问诊结束"),
    WAIT_CONFIRM("11", "等待医生确诊"),
    HIS_CONFIRMED("12", "医生确诊");

    private String value;
    private String text;

    DiagnosisStatus(String value, String text) {
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
    
	public static DiagnosisStatus findByValue(String value) {
		for(DiagnosisStatus item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		if(StringUtils.isEmpty(value)) {
			return null;
		}
		String text = null;
		DiagnosisStatus item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
