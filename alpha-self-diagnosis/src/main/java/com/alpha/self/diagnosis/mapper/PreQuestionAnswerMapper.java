package com.alpha.self.diagnosis.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestionAnswer;

import java.util.List;

public interface PreQuestionAnswerMapper extends MyMapper<PreQuestionAnswer> {

    List<PreQuestionAnswer> getByQuestionCode(PreQuestionAnswer param);
}
