package com.alpha.self.diagnosis.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.google.common.collect.Lists;

/**
 * 页面通过输入框或滑动选择一个答案后，跳转到指定的下一个问题
 */
public class MedicineQuestionUtils {

    private static List<MainSympQuestionForward> mainSympQuestionForwardList = Lists.newArrayList();

    static {
        MainSympQuestionForward mainSympQuestionForward = new MainSympQuestionForward(MainSymptomEnum.呕吐, "2002", "1", "3356");
        MainSympQuestionForward mainSympQuestionForward2 = new MainSympQuestionForward(MainSymptomEnum.阴道流血, "3849", "", "3529");
        MainSympQuestionForward mainSympQuestionForward3 = new MainSympQuestionForward(MainSymptomEnum.阴道流血, "3797", "", "3529");
        MainSympQuestionForward mainSympQuestionForward4 = new MainSympQuestionForward(MainSymptomEnum.阴道流血, "3798", "", "3529");
        MainSympQuestionForward mainSympQuestionForward5 = new MainSympQuestionForward(MainSymptomEnum.下腹部疼痛, "3851", "", "3709");

        mainSympQuestionForwardList.add(mainSympQuestionForward);
        mainSympQuestionForwardList.add(mainSympQuestionForward2);
        mainSympQuestionForwardList.add(mainSympQuestionForward3);
        mainSympQuestionForwardList.add(mainSympQuestionForward4);
        mainSympQuestionForwardList.add(mainSympQuestionForward5);
    }

    public static String getNextQuestionCodeByAnswerTitle(String mainSympCode, String questionCode, String answerTitle) {
        String nextQuestionCode = "";
        MainSymptomEnum mainSymptomEnum = MainSymptomEnum.findByValue(mainSympCode);
        if(mainSymptomEnum != null) {
            Predicate<MainSympQuestionForward> predicate = e -> {
                if(StringUtils.isEmpty(e.getAnswerTitle())) {
                    return e.getMainSymptomEnum() == mainSymptomEnum && e.getQuestionCode().equals(questionCode);
                }
                return e.getMainSymptomEnum() == mainSymptomEnum && e.getQuestionCode().equals(questionCode) && e.getAnswerTitle().equals(answerTitle);
            };
            nextQuestionCode = mainSympQuestionForwardList.stream().filter(predicate).findAny().map(MainSympQuestionForward::getNextQuestionCode).orElseGet(String::new);
        }
        return nextQuestionCode;
    }
}

class MainSympQuestionForward {
    private MainSymptomEnum mainSymptomEnum;
    private String questionCode;
    private String answerTitle;
    private String nextQuestionCode;

    public MainSympQuestionForward(MainSymptomEnum mainSymptomEnum, String questionCode, String answerTitle, String nextQuestionCode) {
        this.mainSymptomEnum = mainSymptomEnum;
        this.questionCode = questionCode;
        this.answerTitle = answerTitle;
        this.nextQuestionCode = nextQuestionCode;
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

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public String getNextQuestionCode() {
        return nextQuestionCode;
    }

    public void setNextQuestionCode(String nextQuestionCode) {
        this.nextQuestionCode = nextQuestionCode;
    }
}
