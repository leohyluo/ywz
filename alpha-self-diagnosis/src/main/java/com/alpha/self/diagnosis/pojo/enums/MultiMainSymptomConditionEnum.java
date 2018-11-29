package com.alpha.self.diagnosis.pojo.enums;

/**
 * 主症状的病程编码
 */
public enum MultiMainSymptomConditionEnum {

    腹泻(MainSymptomEnum.腹泻, "3319", ConstructMainSympType.病程.getCode()),
    发热(MainSymptomEnum.发热, "3327", ConstructMainSympType.病程.getCode()),
    咳嗽(MainSymptomEnum.咳嗽, "3380", ConstructMainSympType.病程.getCode()),
    体重下降(MainSymptomEnum.体重下降, "3621", ConstructMainSympType.病程.getCode()),
    皮疹(MainSymptomEnum.皮疹, "3619", ConstructMainSympType.病程.getCode()),
    血尿(MainSymptomEnum.血尿, "3618", ConstructMainSympType.病程.getCode()),
    呼吸困难(MainSymptomEnum.呼吸困难, "3615", ConstructMainSympType.病程.getCode()),
    咽痛(MainSymptomEnum.咽痛, "3613", ConstructMainSympType.病程.getCode()),
    抽搐(MainSymptomEnum.抽搐, "3847", ConstructMainSympType.次数.getCode()),
    头痛(MainSymptomEnum.头痛, "3608", ConstructMainSympType.病程.getCode()),
    呕吐(MainSymptomEnum.呕吐, "3605", ConstructMainSympType.病程.getCode()),
    腹痛(MainSymptomEnum.腹痛, "3606", ConstructMainSympType.病程.getCode()),
    痛经(MainSymptomEnum.痛经, "3736", ConstructMainSympType.病程.getCode()),
    外阴瘙痒(MainSymptomEnum.外阴瘙痒, "3733", ConstructMainSympType.病程.getCode()),
    阴道流血(MainSymptomEnum.阴道流血, "3703", ConstructMainSympType.病程.getCode()),
    下腹部肿块(MainSymptomEnum.下腹部肿块, "3732", ConstructMainSympType.病程.getCode()),
    下腹部疼痛(MainSymptomEnum.下腹部疼痛, "3713", ConstructMainSympType.病程.getCode()),
    外阴疼痛(MainSymptomEnum.外阴疼痛, "3858", ConstructMainSympType.病程.getCode()),
    白带异常(MainSymptomEnum.白带异常, "3706", ConstructMainSympType.病程.getCode()),
    便秘(MainSymptomEnum.便秘, "3687", ConstructMainSympType.病程.getCode()),
    厌食(MainSymptomEnum.厌食, "3698", ConstructMainSympType.病程.getCode()),
    口腔疱疹(MainSymptomEnum.口腔疱疹, "3685", ConstructMainSympType.病程.getCode()),
    鼻塞(MainSymptomEnum.鼻塞, "3766", ConstructMainSympType.病程.getCode()),
    流涕(MainSymptomEnum.流涕, "3860", ConstructMainSympType.病程.getCode()),
    鼻出血(MainSymptomEnum.鼻出血, "3772", ConstructMainSympType.病程.getCode()),
    打鼾(MainSymptomEnum.打鼾, "3789", ConstructMainSympType.病程.getCode()),
    耳痛(MainSymptomEnum.耳痛, "3783", ConstructMainSympType.病程.getCode()),
    声音嘶哑(MainSymptomEnum.声音嘶哑, "3812", ConstructMainSympType.病程.getCode());

    private MainSymptomEnum mainSymptomEnum;
    private String questionCode;
    private String constructType;   //构成主诉的问题类型，1病程 2次数

    MultiMainSymptomConditionEnum(MainSymptomEnum mainSymptomEnum, String questionCode, String constructType) {
        this.mainSymptomEnum = mainSymptomEnum;
        this.questionCode = questionCode;
        this.constructType = constructType;
    }

    public static MultiMainSymptomConditionEnum findByMainSymptomCode(String mainSympCode) {
        for(MultiMainSymptomConditionEnum item : values()) {
            if(item.mainSymptomEnum.getValue().equals(mainSympCode)) {
                return item;
            }
        }
        return null;
    }

    public MainSymptomEnum getMainSymptomEnum() {
        return mainSymptomEnum;
    }

    public void setMainSymptomEnum(MainSymptomEnum mainSymptomEnum) {
        this.mainSymptomEnum = mainSymptomEnum;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getConstructType() {
        return constructType;
    }

    public void setConstructType(String constructType) {
        this.constructType = constructType;
    }
}
