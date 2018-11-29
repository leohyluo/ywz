package com.alpha.self.diagnosis.service;

import com.alpha.self.diagnosis.pojo.BasicAnswer;

import java.util.List;

public interface BasicAnswerService {

    List<BasicAnswer> findByQuestionCode(String questionCode);
}
