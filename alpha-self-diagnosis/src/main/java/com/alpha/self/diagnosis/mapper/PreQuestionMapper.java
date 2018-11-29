package com.alpha.self.diagnosis.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;

import java.util.List;

public interface PreQuestionMapper extends MyMapper<PreQuestion> {

    /**
     * 查询问题
     * @param questionCode
     * @return
     */
    PreQuestion getByQuestionCode(String questionCode);

    /**
     * 获取下一个问题
     * @param question
     * @return
     */
    PreQuestion getNextQuestion(PreQuestion question);

    /**
     * 获取子问题列表
     * @param question
     * @return
     */
    List<PreQuestion> getSubQuestions(PreQuestion question);
}
