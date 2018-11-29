package com.alpha.self.diagnosis.processor;

import com.alpha.self.diagnosis.pojo.BasicQuestion;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.IQuestionVo;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.service.UserBasicRecordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractBasicAnswerProcessor {

    protected String DEFAULT_ANSWER = "default";
    @Resource
    private UserBasicRecordService userBasicRecordService;

    private static Map<String, AbstractBasicAnswerProcessor> processorMap;

    static {
        processorMap = new HashMap<>();
    }

    protected void register(AbstractBasicAnswerProcessor obj) {
        processorMap.put(setQuestionCode(), obj);
    }

    protected String getUserName(Long diagnosisId, UserInfo userInfo) {
        UserBasicRecord record = userBasicRecordService.findByDiagnosisId(diagnosisId);
        String userName = userInfo.getUserName();
        if (record != null && record.getUserId().longValue() == userInfo.getUserId().longValue()) {
            userName = "您";
        }
        return userName;
    }

    protected abstract String setQuestionCode();

    public IQuestionVo build(Long diagnosisId, BasicQuestion question, UserInfo userInfo) {
        Map<String, List<IAnswerVo>> data = getAnswers(diagnosisId, question, userInfo);
        IQuestionVo questionVo = getQuestionVo(diagnosisId, question, userInfo, data);
        return questionVo;
    }

    /**
     * 获取问题下的答案(有可能数据源不一样，响应结果不一样)
     *
     * @param question
     * @param userInfo
     * @return
     */
    protected abstract Map<String, List<IAnswerVo>> getAnswers(Long diagnosisId, BasicQuestion question, UserInfo userInfo);

    protected abstract IQuestionVo getQuestionVo(Long diagnosisId, BasicQuestion question, UserInfo userInfo, Map<String, List<IAnswerVo>> data);
}
