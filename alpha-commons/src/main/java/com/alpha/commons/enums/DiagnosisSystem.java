package com.alpha.commons.enums;

public enum DiagnosisSystem {

	呼吸内科(1,"呼吸内科"),
	消化内科(2,"消化内科"),
	神经内科(3,"神经内科"),
	心血管内科(4,"心血管内科"),
	血液病科(5,"血液病科"),
	肾脏内科(6,"肾脏内科"),
	内分泌科(7,"内分泌科"),
	风湿免疫科(8,"风湿免疫科"),
	肿瘤内科(9,"肿瘤内科"),
	普外科(10,"普外科"),
	神经外科(11,"神经外科"),
	骨科(12,"骨科"),
	泌尿外科(13,"泌尿外科"),
	胸外科(14,"胸外科"),
	烧伤科(15,"烧伤科"),
	新生儿科(16,"新生儿科"),
	眼科(17,"眼科"),
	耳鼻喉科(18,"耳鼻喉科"),
	皮肤病科(19,"皮肤病科"),
	感染科(21,"感染科"),
	综合内科(22,"综合内科"),
	口腔科(23,"口腔科"),
	中医科(24,"中医科"),
	儿童保健科(25,"儿童保健科"),
	青春期妇科门诊(26,"青春期妇科门诊"),
	妇科炎症(27,"妇科炎症"),
	妇科肿瘤(28,"妇科肿瘤"),
	妇科内分泌(29,"妇科内分泌"),
	妊娠(30,"妊娠"),
	神经系统(31,"神经系统"),
	泌尿系统(32,"泌尿系统");

    private Integer value;
    private String text;

    DiagnosisSystem(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

	public static DiagnosisSystem findByValue(Integer value) {
		for(DiagnosisSystem item : values()) {
			if(item.value == value) {
				return item;
			}
		}
		return null;
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
}
