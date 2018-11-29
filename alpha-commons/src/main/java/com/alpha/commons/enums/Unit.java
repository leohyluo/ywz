package com.alpha.commons.enums;

/**
 * 
 * 单位类型
 */
public enum Unit {

	MINUTE("minute", "分钟"),//分
	HOUR("hour", "小时"),	//小时
	DAY("day", "天"),		//日
	WEEK("week", "周"),	//周
    MONTH("month", "月"),		//月
    SEASON("season", "季"),	//季节
    YEAR("year", "年"),			//年
    CENTIGRADE("centigrade", "摄氏度"),
    TEMPERATURE("temperature", "℃"),
    NUM_OF_TIMES("times","次");	

	private String value;
    private String text;

    Unit(String value, String text) {
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
    
	public static Unit findByValue(String value) {
		for(Unit item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static Unit findByText(String text) {
		for(Unit item : values()) {
			if(item.text.equals(text)) {
				return item;
			}
		}
		return null;
	}
	
	public static Unit containText(String text) {
		for(Unit item : values()) {
			if(text.contains(item.text)) {
				return item;
			}
		}
		return null;
	}
}
