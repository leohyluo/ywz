package com.alpha.self.diagnosis.pojo.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xc.xiong on 2017/9/1.
 * 问题类型
 */
public enum QuestionEnum {

    介绍信息(58,2),//Hi，我是{doctorName}医生的助手，我将模拟医生的专业问诊，协助医生提前全面了解{userName}的病情，您的回答，有助于医生为{userName}提供更精准的诊疗。
    引言(59, 2),  //在回答过程中，对于不确定的问题，请选择“不清楚”，医生问诊时会详细询问。
    初诊(60,2),
    完善信息(61,2),
    出生日期(62,2),
    性别(63,2),
    体重(64,2),
    鼓励1(65,2),
    一般情况(66,2),
    精神情况(67,2),
    食欲情况(68,2),
    大便情况(69,2),
    小便情况(70,2),
    鼓励2(71,2),
    既往史(72,2),
    过敏史(73,2),
    出生史(74,2),
    胎龄(75,2),
    喂养史(76,2),
    预防接种史(77,2),
    是否处于月经期(78,2),
    谢谢您(79,2),//谢谢您！确认后立即推送给{doctorName}医生，基于您填写的信息与医生进一步沟通，将有助于为{userName}选择出最合适的治疗方案。
    月经史(80,2),
    月经初潮(81,2),
    月经周期(82,2),
    经期(83,2),
    末次月经时间(84,2),
    婚育史(85,2),
    足月产孩子个数(86,2),
    早产孩子个数(88,2),
    流产孩子个数(89,2),
    现存孩子个数(90,2),
    主症状语义分析(99,1),
    主症状(100,1),
    医学问题(101,1),
    伴随症状(102,1),
    年龄问题(103,1),
    季节问题(104,1),
    常见伴随症状(105,1),
    附加医学问题(106,1),
    诊断结果(200,1);

    private Integer value;
    private Integer questionClass; //问题分类 1:诊断问题 2:基础问题 3:提示性问题

    QuestionEnum(Integer value,Integer questionClass) {
        this.value = value;
        this.questionClass = questionClass;
    }

    public int getOrdinal() {
        return this.ordinal();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getQuestionClass() {
        return questionClass;
    }

    public void setQuestionClass(Integer questionClass) {
        this.questionClass = questionClass;
    }

    public static QuestionEnum getQuestion(int type) {
        QuestionEnum qs[] = QuestionEnum.values();
        for (QuestionEnum q : qs) {
            if (q.getValue() == type)
                return q;
        }
        return null;
    }

    public static boolean isDiagnosisQuestion(Integer questionType) {
        QuestionEnum question = null;
        for(QuestionEnum item : values()) {
            if(item.value.intValue() == questionType.intValue()) {
                question = item;
                break;
            }
        }
        if(question == null) {
            return false;
        }
        return question.questionClass == 1 ? true : false;
    }

    public static boolean isBasicQuestion(Integer questionType) {
        QuestionEnum question = null;
        for(QuestionEnum item : values()) {
            if(item.value.intValue() == questionType.intValue()) {
                question = item;
                break;
            }
        }
        if(question == null) {
            return false;
        }
        return question.questionClass == 2 ? true : false;
    }
}
