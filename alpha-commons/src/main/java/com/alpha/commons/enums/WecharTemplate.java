package com.alpha.commons.enums;

/**
 * 微信模板编码
 * @author Administrator
 *
 */
public enum WecharTemplate {
	//个人
	//ACTIVE_QUEUE("IFG46lvfIcQPjTHR8UgQPcXaZkzn7u4NazzAxe5xK7Q"),	//已有排队信息的模板编号
	//公司
	ACTIVE_QUEUE("x_bb8CfM1N5WcDqYmvJ4gmAfD17ZFxPYwoYKJgmCYkI"),	//已有排队信息的模板编号
    UNACTIVE_QUEUE("2XHVQiomm6xNJ2XR1X1_bl3d0B5CF2R4X89cXpi8A9M"); //要到护士站进行激活的模板编号

    private String value;

    WecharTemplate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
	public static WecharTemplate findByValue(String value) {
		for(WecharTemplate item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
}
