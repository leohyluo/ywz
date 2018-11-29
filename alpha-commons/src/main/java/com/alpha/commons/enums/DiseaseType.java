package com.alpha.commons.enums;

public enum DiseaseType {

	PASTMEDICALHISTORY(0),			//既往史
	ALLERGICHISTORY(1);			//过敏史
	
	private int value;
	
	private DiseaseType(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

}
