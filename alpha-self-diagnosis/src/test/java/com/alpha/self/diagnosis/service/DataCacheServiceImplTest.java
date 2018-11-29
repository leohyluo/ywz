package com.alpha.self.diagnosis.service;
import com.alibaba.fastjson.JSON;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCacheServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testObject2Json() {
        PreQuestion preQuestion = new PreQuestion();
        preQuestion.setQuestionCode("998");
        preQuestion.setTitle("在回答过程中，对于不确定的问题，请选择“不清楚”，医生问诊时会详细询问。");

        String jsonStr = JSON.toJSONString(preQuestion);
        logger.info(jsonStr);
    }

    @Test
    public void testJson2Object() {
        Person person = new Person();
        //person.setName("张三");
        person.setGender(1);
        String jsonStr = JSON.toJSONString(person);

        try {
            Person person1 = JSON.parseObject(jsonStr, Person.class);
            logger.info(person1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("字符串转为对象完毕");
    }
}

class Person {
    private String name;
    private Integer gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
