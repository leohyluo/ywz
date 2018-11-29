package com.alpha.self.diagnosis.processor;

import com.alpha.commons.enums.BasicQuestionType;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.self.diagnosis.annotation.BasicAnswerProcessor;
import com.alpha.self.diagnosis.dao.DiagnosisAllergicHistoryDao;
import com.alpha.self.diagnosis.pojo.BasicAnswer;
import com.alpha.self.diagnosis.pojo.BasicQuestion;
import com.alpha.self.diagnosis.pojo.vo.*;
import com.alpha.self.diagnosis.service.BasicAnswerService;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisAllergicHistory;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.dao.UserBasicRecordDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@BasicAnswerProcessor
@Component
public class AllergicHistoryAnswerProcessor extends AbstractBasicAnswerProcessor {

    private static final String SEARCH_URL = "/data/search/allergicHistory";
    private static final String QUESTION_CODE = BasicQuestionType.ALLERGIC_HISTORY.getValue();

    @Resource
    private BasicAnswerService basicAnswerService;
    @Resource
    private DiagnosisAllergicHistoryDao diagnosisAllergicHistoryDao;
    @Resource
    private UserBasicRecordDao userBasicRecordDao;

    protected Map<String, List<IAnswerVo>> queryAnswers(Long diagnosisId, BasicQuestion question, UserInfo userInfo) {
        int showCount = 7;
        Map<String, List<IAnswerVo>> map = new HashMap<>();
        List<IAnswerVo> showList = new ArrayList<>();
        List<IAnswerVo> searchList = new ArrayList<>();
        Date birth = userInfo.getBirth();
        float age = DateUtils.getAge(birth);

        // 用于存储之前已选择的既往史
        List<SelectedBasicAnswerVo> list0 = new ArrayList<>();
        // 用于存储用户频次超过阀值的既往史
        //List<BasicAnswerVo> list1 = new ArrayList<>();
        // 用于存储用户频次不超过阀值的既往史
        List<BasicAnswerVo> list2 = new ArrayList<>();
        // 用于存储手术史、否/不清楚
        List<BasicAnswerVo> defaultAnswervoList = new ArrayList<>();

        //查询无和不清楚这两个选项
        List<BasicAnswer> answerList = basicAnswerService.findByQuestionCode(question.getQuestionCode());
        defaultAnswervoList = answerList.stream().map(BasicAnswerVo::new).collect(toList());

        // 查询之前已选择的过敏史
        List<DiseaseVo> selectedList = new ArrayList<>();
        Set<String> selectedDiseaseSet = new HashSet<>();
        Map<String, Object> selectedParam = new HashMap<>();

        //查询userbaserecorder
        UserBasicRecord userBasicRecord = userBasicRecordDao.findByDiagnosisId(diagnosisId);
        String selectedPastmedicalHistoryCode = userBasicRecord.getAllergicHistoryCode();
        List<DiseaseVo> distinctSelectedList = new ArrayList<>();

        if (StringUtils.isNotEmpty(selectedPastmedicalHistoryCode)) {
            if ("0".equals(selectedPastmedicalHistoryCode) || "-1".equals(selectedPastmedicalHistoryCode)){
                defaultAnswervoList = defaultAnswervoList.stream().peek(e->{
                    if(e.getAnswerValue().equals(selectedPastmedicalHistoryCode)) {
                        e.setChecked("Y");
                    }
                }).collect(toList());
            } else {
                List<String> diseaseCodeList = Stream.of(selectedPastmedicalHistoryCode.split(",")).collect(toList());
                //selectedDiseaseSet = diseaseCodeList.stream().collect(Collectors.toSet());
                selectedParam.put("userAllergicHistoryCode", diseaseCodeList);
                selectedList = diagnosisAllergicHistoryDao.querySelectedAllergicHistory(selectedParam);

                for(DiseaseVo diseaseVo : selectedList) {
                    if(!selectedDiseaseSet.contains(diseaseVo.getDiseaseCode())) {
                        distinctSelectedList.add(diseaseVo);
                        selectedDiseaseSet.add(diseaseVo.getDiseaseCode());
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(distinctSelectedList)) {
            list0 = distinctSelectedList.stream().map(SelectedBasicAnswerVo::new).collect(toList());
        }

        int limit = showCount - selectedList.size() - defaultAnswervoList.size();
        //查询过敏史大类
        //limit = showCount - selectedList.size() - defaultAnswervoList.size() - list1.size();
        if (limit > 0) {
            Map<String, Object> param = userInfo.toBasicMap();
            param.put("gender", userInfo.getGender());
            param.put("age", age);
            param.put("limitSize", 2);
            List<DiagnosisAllergicHistory> list = diagnosisAllergicHistoryDao.queryAllergicHistory(param);
            list2 = list.stream().map(BasicAnswerVo::new).collect(toList());
        }
        final Set<String> selectedDiseaseSet2 = selectedDiseaseSet;
        list2 = list2.stream().filter(e->!selectedDiseaseSet2.contains(e.getAnswerValue())).collect(toList());

        //拼装展示用的数据
        showList.addAll(defaultAnswervoList);
        showList.addAll(list0);
        //showList.addAll(list1);
        if (CollectionUtils.isNotEmpty(list2)) {
            showList.addAll(list2);
        }

        map.put("show", showList);
        map.put("search", searchList);
        return map;
    }

    @Override
    protected Map<String, List<IAnswerVo>> getAnswers(Long diagnosisId, BasicQuestion question, UserInfo userInfo) {
        return this.queryAnswers(diagnosisId, question, userInfo);
    }

    @Override
    protected IQuestionVo getQuestionVo(Long diagnosisId, BasicQuestion question, UserInfo userInfo,
                                        Map<String, List<IAnswerVo>> data) {
        List<IAnswerVo> showList = data.get("show");
        String userName = getUserName(diagnosisId, userInfo);
        BasicQuestionWithSearchVo questionvo = new BasicQuestionWithSearchVo(diagnosisId, question, showList, userInfo, userName);
        questionvo.setSearchUrl(SEARCH_URL);
        return questionvo;
    }

    @Override
    protected String setQuestionCode() {
        return QUESTION_CODE;
    }

}
