package com.alpha.self.diagnosis.mapper;

import com.alpha.self.diagnosis.pojo.BasicQuestion;

import java.util.List;

public interface BasicQuestionMapper {

    BasicQuestion find(BasicQuestion question);

    BasicQuestion findByQuestionCode(String questionCode);

    List<BasicQuestion> findNext(BasicQuestion question);
}
