package com.alpha.commons.enums;

public enum Channel {

	HTML5(0),			//html5
	ZHYS(1),			//智慧药师
	HOSPITAL(2);		//医院
	
	private int value;
	
	private Channel(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}



	public void setValue(int value) {
		this.value = value;
	}

}
