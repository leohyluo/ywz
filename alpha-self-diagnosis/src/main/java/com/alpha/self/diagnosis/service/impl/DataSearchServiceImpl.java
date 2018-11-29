package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.core.sql.DataSet;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.util.py4j.Py4j;
import com.alpha.self.diagnosis.dao.*;
import com.alpha.self.diagnosis.mapper.SearchKeyMapper;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.BasicAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.service.AnswerService;
import com.alpha.self.diagnosis.service.DataSearchService;
import com.alpha.server.rpc.diagnosis.pojo.*;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 病史业务逻辑处理
 */
@Service
public class DataSearchServiceImpl implements DataSearchService {

    @Resource
    private DiagnosisPastmedicalHistoryDao diagnosisPastmedicalHistoryDao;
    @Resource
    private DiagnosisAllergicHistoryDao diagnosisAllergicHistoryDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;
    @Resource
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;
    @Resource(name = "diagnosisQuestionAnswerServiceImpl")
    private AnswerService diagnosisQuestionAnswerService;
    @Autowired
    private SearchKeyMapper searchKeyMapper;
    @Resource
    private DiagnosisMainsympConcsympDao diagnosisMainsympConcsympDao;

    @Override
    public List<IAnswerVo> searchPastMedicalHistory(UserInfo userInfo, String keyword) {
        Map<String, Object> param = new HashMap<>();
        if (userInfo != null) {
            float age = DateUtils.getAge(userInfo.getBirth());
            param.put("gender", userInfo.getGender());
            param.put("age", age);
        }
        String diseaseName = keyword;
        diseaseName = diseaseName.contains("%") ? diseaseName.replace("%", "\\%") : diseaseName;
        param.put("diseaseName", diseaseName);
        param.put("limitSize", 5);

        List<IAnswerVo> answerList = new ArrayList<>();
        List<BasicAnswerVo> list1 = new ArrayList<>();

        List<DiagnosisPastmedicalHistory> pastmedicalHistory = diagnosisPastmedicalHistoryDao.searchPastmedicalHistory(param);
        list1 = pastmedicalHistory.stream().map(BasicAnswerVo::new).collect(Collectors.toList());

        answerList.addAll(list1);
        return answerList;
    }

    @Override
    public List<IAnswerVo> searchAllergyHistory(UserInfo userInfo, String keyword) {
        Map<String, Object> param = new HashMap<>();
        if (userInfo != null) {
            float age = DateUtils.getAge(userInfo.getBirth());
            param.put("gender", userInfo.getGender());
            param.put("age", age);
        }
        String diseaseName = keyword;
        diseaseName = diseaseName.contains("%") ? diseaseName.replace("%", "\\%") : diseaseName;

        param.put("diseaseName", diseaseName);
        param.put("limitSize", 5);

        List<IAnswerVo> answerList = new ArrayList<>();
        List<BasicAnswerVo> list1 = new ArrayList<>();

        List<DiagnosisAllergicHistory> allergicHistoryList = diagnosisAllergicHistoryDao.queryAllergicHistory(param);
        list1 = allergicHistoryList.stream().map(BasicAnswerVo::new).collect(Collectors.toList());
        answerList.addAll(list1);
        return answerList;
    }

    @Override
    public DiagnosisMainSymptoms searchMainSymptom(SearchKeys searchKeys) {
        //查询搜索的词是否在主症状内
        DataSet<DiagnosisMainSymptoms> dmsDateSet = diagnosisMainSymptomsDao.selectLimit(1, 1000);
        List<DiagnosisMainSymptoms> diagnosisMainSymptomses = dmsDateSet.getRows();
        Set<String> names = diagnosisMainSymptomses.stream().filter(e->e.getIsShow() != null).filter(e->e.getIsShow() == 1).map(DiagnosisMainSymptoms::getSympName).collect(Collectors.toSet());
        if (names.contains(searchKeys.getKeys())) {
            DiagnosisMainSymptoms diagnosisMainSymptoms = diagnosisMainSymptomses.stream().filter(e -> e.getSympName().equals(searchKeys.getKeys())).findFirst().get();
            return diagnosisMainSymptoms;
        } else {
            searchKeyMapper.searchKey(searchKeys);
        }
        return null;
    }

    @Override
    public LinkedHashSet<IAnswerVo> searchCocnSymptom(Long diagnosisId, String mainSympCode, UserInfo userInfo, String keyword) {
        List<DiagnosisMainsympConcsymp> dmcs = this.listConcsymp(mainSympCode, userInfo);
        //过滤常见伴随症状
        List<DiagnosisMainsympQuestion> dmQuestions = diagnosisMainsympQuestionDao.listDiagnosisMainsympQuestion(mainSympCode);
        Optional<DiagnosisMainsympQuestion> questionOptional = dmQuestions.stream().filter(e->e.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue()).findFirst();
        if(questionOptional.isPresent()) {
            String questionCode4NormalCocnSympCode = questionOptional.get().getQuestionCode();
            //List<DiagnosisQuestionAnswer> normalCocnSympAnswers = medicineAnswerService.listDiagnosisQuestionAnswer(mainSympCode, questionCode4NormalCocnSympCode, userInfo);
            LinkedHashSet<DiagnosisQuestionAnswer> normalCocnSympAnswers = diagnosisQuestionAnswerService.get(diagnosisId, mainSympCode,questionCode4NormalCocnSympCode, userInfo);
            Set<String> normalCocnSympAnswerSet = normalCocnSympAnswers.stream().map(DiagnosisQuestionAnswer::getContent).distinct().collect(Collectors.toSet());
            dmcs = dmcs.stream().filter(e->!normalCocnSympAnswerSet.contains(e.getSympName())).collect(Collectors.toList());
        }
        //过滤主症状
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sympCode", mainSympCode);
        List<DiagnosisMainSymptoms> mainSymptomsList = diagnosisMainSymptomsDao.query(paramMap);
        Set<String> mainSympNameSet = mainSymptomsList.stream().map(DiagnosisMainSymptoms::getSympName).distinct().collect(Collectors.toSet());
        dmcs = dmcs.stream().filter(e->!mainSympNameSet.contains(e.getSympName())).collect(Collectors.toList());

        LinkedHashSet<IAnswerVo> answerVos = new LinkedHashSet<>();

        Iterator<DiagnosisMainsympConcsymp> it = dmcs.iterator();
        Py4j py = new Py4j();
        while (it.hasNext()) {
            DiagnosisMainsympConcsymp dmc = it.next();
            //判断关键词是否被包含
            if(StringUtils.isChinese(keyword.toUpperCase())){
                if(dmc.getSympName().indexOf(keyword) < 0){
                    it.remove();
                }
            }
            else{
                String py4SympName = Py4j.getPyinPreFix(dmc.getSympName());
                String py4PopuName = Py4j.getPyinPreFix(StringUtils.toString(dmc.getPopuName()));
                if(py4SympName.toUpperCase().indexOf(keyword.toUpperCase()) < 0 && py4PopuName.toUpperCase().indexOf(keyword.toUpperCase()) < 0) {
                    it.remove();
                }
            }
        }
        ////modify by wfh 20180524 修改当主诉为“发热”时且常见伴随症状选择“咳嗽”时，还有哪些症状答案中不应出现“咳痰”答案项
        if(mainSympCode.equals("55")){
            dmcs = dmcs.stream().filter(x -> !x.getSympName().equals("咳痰")).collect(Collectors.toList());
        }

        for (DiagnosisMainsympConcsymp dmc : dmcs) {
            BasicAnswerVo answer = new BasicAnswerVo(dmc);
            answerVos.add(answer);
        }
        return answerVos;
    }

    /**
     * 查询主症状下所有的伴随症状名称，
     *
     * @param mainSympCode
     * @return
     */
    private List<DiagnosisMainsympConcsymp> listConcsymp(String mainSympCode, UserInfo userInfo) {
        List<DiagnosisMainsympConcsymp> dmcs = diagnosisMainsympConcsympDao.listDiagnosisMainsympConcsymp(mainSympCode);
        for (Iterator iterator = dmcs.iterator(); iterator.hasNext(); ) {
            DiagnosisMainsympConcsymp answer = (DiagnosisMainsympConcsymp) iterator.next();
            if (answer.getGender() != null && answer.getGender() > 0 && answer.getGender() != userInfo.getGender()) {
                iterator.remove();
                continue;//过滤性别
            }
            float age = DateUtils.getAge(userInfo.getBirth());
            if ((answer.getMinAge() != null && answer.getMinAge() > age) || (answer.getMaxAge() != null && answer.getMaxAge() < age)) {
                iterator.remove();
                continue;//过滤年龄
            }
        }
        return dmcs;
    }
}
