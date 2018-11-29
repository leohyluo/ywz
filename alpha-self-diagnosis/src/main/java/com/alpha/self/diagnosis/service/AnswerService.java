package com.alpha.self.diagnosis.service;

import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.ReplyQuestionVo;
import com.alpha.server.rpc.user.pojo.UserInfo;

import java.util.LinkedHashSet;

/**
 * 医学问题答案处理类
 */
public interface AnswerService {


    /**
     * 获取问题下的所有答案（已去重、排序）
     * @param mainSympCode
     * @param questionCode
     * @param userInfo
     * @return
     */
    <T> LinkedHashSet<T> get(Long diagnosisId, String mainSympCode, String questionCode, UserInfo userInfo);

    /**
     * 获取问题下的所有答案并将其转换成页面显示的数据视图
     * @param
     * @return
     */
    LinkedHashSet<IAnswerVo> getAnswerView(Long diagnosisId, String mainSympCode, String questionCode, UserInfo userInfo);
}
