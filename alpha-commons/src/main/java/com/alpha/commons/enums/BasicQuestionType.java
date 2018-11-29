package com.alpha.commons.enums;

/**
 * Created by xc.xiong on 2017/9/1.
 * 问题类型
 */
public enum BasicQuestionType {

	DEFAULT("0"),
    BORN("1001"),					//{userName}哪年出生的?
    BOY_OR_GIRL("1002"),			//{userName}是男孩还是女孩?
    MAN_OR_WOMAN("1003"),			//{userName}是帅哥还是美女?
    MENSTRUAL_PERIOD("1004"),		//{userName}是否处于月经期?
    SPECIAL_PERIOD("1005"),		//{userName}是否处于女性特殊时期?
    PAST_MEDICAL_HISTORY("1006"),	//曾经得过哪些疾病?
    ALLERGIC_HISTORY("1007"),		//是否存在过敏史?
    FERTILITY_TYPE("1008"),		//{userName}是顺产、剖腹产还是产钳助产?
    GESTATIONAL_AGE("1009"),		//{userName}出生时胎龄是多少周?
    FEED_TYPE("1010"),			//{userName}目前的喂养状况如何?
    HEIGHT("1011"),				//{userName}目前的身高是多少?
    WEIGHT("1012"),				//{userName}目前的体重是多少?
    LIVER_RENAL("1013"),			//近期检查肝肾功能是否正常?(对于用药安全很重要哦)
    VACCINATION_HISTORY("1015");	//预防接种史

    private String value;

    BasicQuestionType(String value) {
        this.value = value;
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
    
    public static BasicQuestionType findByValue(String value) {
		for(BasicQuestionType item : values()) {
			if(item.value.equals(value)) {
				return item;
			}
		}
		return null;
	}
}
