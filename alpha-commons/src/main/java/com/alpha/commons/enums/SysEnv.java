package com.alpha.commons.enums;


import com.alpha.commons.util.StringUtils;

public enum SysEnv {
	COM_DEV("com_dev", "公司开发环境"),
	COM_TEST("com_test", "公司测试环境"),
    COM_PPE("com_ppe", "公司准生产环境"),
	HIS_TEST("his_test", "医院测试环境"),
	HIS_ONLINE("his_online", "医院线上环境"),

	F_SZET_ZHK("F_SZET_ZHK", "深圳市儿童医院综合科"),
	F_BJ_FK("F_BJ_FK", "北京妇科");

    private String value;
    private String text;

    SysEnv(String value, String text) {
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
    
	public static SysEnv findByValue(String value) {
		for(SysEnv item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
	
	public static String getText(String value) {
		String text = null;
		SysEnv item = findByValue(value);
		if(item != null) {
			text = item.getText();
		}
		return text;
	}

	public static Boolean isCompanyEnv(String env) {
		if(StringUtils.isEmpty(env))
			return false;
    	if(env.equals(SysEnv.COM_DEV.getValue()) || env.equals(SysEnv.COM_TEST.getValue()) || env.equals(SysEnv.COM_PPE.getValue())) {
    		return true;
		}
		return false;
	}

	public static Boolean isHisEnv(String env) {
		if(StringUtils.isEmpty(env))
			return false;
		if(env.equals(SysEnv.HIS_TEST.getValue()) || env.equals(SysEnv.HIS_ONLINE.getValue())) {
			return true;
		}
		return false;
	}
}
