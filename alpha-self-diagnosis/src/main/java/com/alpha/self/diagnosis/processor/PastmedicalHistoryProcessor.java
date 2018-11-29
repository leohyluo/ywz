package com.alpha.self.diagnosis.processor;

import com.alpha.commons.enums.BasicQuestionType;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.self.diagnosis.annotation.BasicAnswerProcessor;
import com.alpha.self.diagnosis.dao.DiagnosisPastmedicalHistoryDao;
import com.alpha.self.diagnosis.pojo.BasicAnswer;
import com.alpha.self.diagnosis.pojo.BasicQuestion;
import com.alpha.self.diagnosis.pojo.vo.*;
import com.alpha.self.diagnosis.service.BasicAnswerService;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisPastmedicalHistory;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.dao.UserBasicRecordDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@BasicAnswerProcessor
@Component
public class PastmedicalHistoryProcessor extends AbstractBasicAnswerProcessor {

    @Resource
    private BasicAnswerService basicAnswerService;
    @Resource
    private DiagnosisPastmedicalHistoryDao diagnosisPastmedicalHistoryDao;
    @Resource
    private UserBasicRecordDao userBasicRecordDao;

    private static final String SEARCH_URL = "/data/search/pastmedicalHistory";
    private static final String QUESTION_CODE = BasicQuestionType.PAST_MEDICAL_HISTORY.getValue();

    protected Map<String, List<IAnswerVo>> queryAnswers(Long diagnosisId, BasicQuestion question, UserInfo userInfo) {
        int showCount = 7;
        Map<String, List<IAnswerVo>> map = new HashMap<>();
        List<IAnswerVo> showList = new ArrayList<>();
        List<IAnswerVo> searchList = new ArrayList<>();
        Date birth = userInfo.getBirth();
        float age = DateUtils.getAge(birth);

        //用于存储之前已选择的既往史
        List<SelectedBasicAnswerVo> list0 = new ArrayList<>();
        Set<String> selectedDiseaseSet = new HashSet<>();
        // 用于存储用户频次超过阀值的既往史
        //List<BasicAnswerVo> list1 = new ArrayList<>();
        // 用于存储用户频次不超过阀值的既往史
        List<BasicAnswerVo> list2 = new ArrayList<>();
        // 用于存储手术史、否/不清楚
        List<BasicAnswerVo> defaultAnswervoList = new ArrayList<>();

        //查询之前已选择的既往史
        List<DiseaseVo> selectedList = new ArrayList<>();
        Map<String, Object> selectedParam = new HashMap<>();
       // String selectedPastmedicalHistoryCode = userInfo.getPastmedicalHistoryCode();
        //查询userbaserecorder
        UserBasicRecord userBasicRecord = userBasicRecordDao.findByDiagnosisId(diagnosisId);
        String sympCode = userBasicRecord.getMainSymptomCode();
        String selectedPastmedicalHistoryCode = userBasicRecord.getPastmedicalHistoryCode();

        List<DiseaseVo> distinctSelectedList = new ArrayList<>();
        if (StringUtils.isNotEmpty(selectedPastmedicalHistoryCode)) {
            List<String> diseaseCodeList = Stream.of(selectedPastmedicalHistoryCode.split(",")).collect(toList());
            selectedParam.put("userPastmedicalHistoryCode", diseaseCodeList);
            selectedList = diagnosisPastmedicalHistoryDao.querySelectedPastmedicalHistory(selectedParam);

            for(DiseaseVo diseaseVo : selectedList) {
                if(!selectedDiseaseSet.contains(diseaseVo.getDiseaseCode())) {
                    distinctSelectedList.add(diseaseVo);
                    selectedDiseaseSet.add(diseaseVo.getDiseaseCode());
                }
            }
        }

        if (CollectionUtils.isNotEmpty(distinctSelectedList)) {
            list0 = distinctSelectedList.stream().map(SelectedBasicAnswerVo::new).collect(toList());
        }
        //查出手术史、否/不清楚这两个选项
        List<BasicAnswer> answerList = basicAnswerService.findByQuestionCode(question.getQuestionCode());
        defaultAnswervoList = answerList.stream().filter(e->!e.getAnswerCode().equals("3025")).map(BasicAnswerVo::new).collect(toList());
        Map<String, DiseaseVo> selectedMap = selectedList.stream().collect(Collectors.toMap(DiseaseVo::getDiseaseCode, Function.identity()));
        defaultAnswervoList = defaultAnswervoList.stream().filter(e -> !selectedMap.containsKey(e.getAnswerValue())).collect(toList());
        
        //查询用户频次超过阀值的既往史大类
        int limit = showCount - selectedList.size() - defaultAnswervoList.size();
        //查询按默认排序的既往史大类
        //limit = showCount - selectedList.size() - defaultAnswervoList.size() - list1.size();
        if (limit > 0) {
            Map<String, Object> param = userInfo.toBasicMap();
            param.put("gender", userInfo.getGender());
            param.put("age", age);
            param.put("limitSize", limit);
            param.put("sympCode",sympCode);
            list2 = queryPastMedicalHistory(param);
        }

        //用户之前如没选择过既往史，默认选中"无"
        if(CollectionUtils.isEmpty(list0)) {
        	defaultAnswervoList = defaultAnswervoList.stream().peek(e->{
        		if("-1".equals(e.getAnswerValue())) {
        			e.setChecked("Y");
        		}
        	}).collect(toList());
        }
        list2 = list2.stream().filter(e->!selectedDiseaseSet.contains(e.getAnswerValue())).collect(toList());

        //拼装展示用的数据
        showList.addAll(defaultAnswervoList);
        showList.addAll(list0);
        //showList.addAll(list1);
        if (CollectionUtils.isNotEmpty(list2)) {
            showList.addAll(list2);
        }
        //查询小类既往史
        /*List<DiagnosisSubpastmedicalHistory> subList = diagnosisPastmedicalHistoryService.querySubPastmedicalHistory(param);
        List<BasicAnswerVo> subAnswervoList = subList.stream().map(BasicAnswerVo::new).collect(toList());*/
        //拼装查询 用的数据
        /*searchList.addAll(list1);
		searchList.addAll(defaultAnswervoList);
		searchList.addAll(list2);
		searchList.addAll(subAnswervoList);*/

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
        //List<IAnswerVo> searchList = data.get("search");
        String userName = getUserName(diagnosisId, userInfo);
        //return new DiseaseQuestionVo(diagnosisId, question, showList, searchList, userInfo, userName);
        BasicQuestionWithSearchVo questionvo = new BasicQuestionWithSearchVo(diagnosisId, question, showList, userInfo, userName);
        questionvo.setSearchUrl(SEARCH_URL);
        return questionvo;
    }

    @Override
    protected String setQuestionCode() {
        return QUESTION_CODE;
    }

    private List<BasicAnswerVo> queryPastMedicalHistory(Map<String, Object> param) {
        List<DiagnosisPastmedicalHistory> list = diagnosisPastmedicalHistoryDao.queryPastmedicalHistory(param);
        List<BasicAnswerVo> result = list.stream().map(BasicAnswerVo::new).collect(toList());
        return result;
    }
}
