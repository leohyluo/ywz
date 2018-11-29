package com.alpha.self.diagnosis.mapper;

import com.alpha.self.diagnosis.pojo.BasicAnswer;

import java.util.List;

public interface BasicAnswerMapper {

    List<BasicAnswer> findByQuestionCode(String questionCode);
}
