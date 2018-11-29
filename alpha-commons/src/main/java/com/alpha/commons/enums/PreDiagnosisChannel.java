package com.alpha.commons.enums;


public enum PreDiagnosisChannel {

	QRCODR_OFFLINE("1", "现场扫码"),
	OFFICEACCOUNT("2", "公众号消息");

    private String value;
    private String text;

    PreDiagnosisChannel(String value, String text) {
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
    
	public static PreDiagnosisChannel findByValue(String value) {
		for(PreDiagnosisChannel item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		PreDiagnosisChannel item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}
}
