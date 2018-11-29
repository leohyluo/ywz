package com.alpha.self.diagnosis.pojo.enums;

/**
 * Created by xc.xiong on 2017/9/1.
 * 主症状编码
 */
public enum MainSymptomEnum {
    腹泻("54", "腹泻"),
    发热("55", "发热"),
    咳嗽("57", "咳嗽"),
    体重下降("58", "体重下降"),
    皮疹("59", "皮疹"),
    血尿("60", "血尿"),
    呼吸困难("61", "呼吸困难"),
    咽痛("62", "咽痛"),
    抽搐("63", "抽搐"),
    头痛("64", "头痛"),
    呕吐("65", "呕吐"),
    腹痛("66", "腹痛"),
    痛经("75", "痛经"),
    外阴瘙痒("76", "外阴瘙痒"),
    阴道流血("77", "阴道流血"),
    下腹部肿块("78", "下腹部肿块"),
    下腹部疼痛("79", "下腹部疼痛"),
    外阴疼痛("80", "外阴疼痛"),
    白带异常("85", "白带异常"),
    月经失调("87", "月经失调"),
    便秘("89", "便秘"),
    厌食("92", "厌食"),
    口腔疱疹("91", "口腔疱疹"),
    鼻出血("97", "鼻出血"),
    打鼾("99", "打鼾"),
    鼻塞("102", "鼻塞"),
    耳痛("103", "耳痛"),
    声音嘶哑("105", "声音嘶哑"),
    流涕("111", "流涕");

    private String value;
    private String text;

    public static MainSymptomEnum findByValue(String value) {
        for(MainSymptomEnum item : values()) {
            if(item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }

    MainSymptomEnum(String value, String text) {
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
}
