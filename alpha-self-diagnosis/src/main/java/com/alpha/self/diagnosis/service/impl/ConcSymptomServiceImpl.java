package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.util.py4j.Py4j;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympConcsympDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.mapper.SyCommonConcSymptomMapper;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.pojo.vo.BasicAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.service.AnswerService;
import com.alpha.self.diagnosis.service.SymptomAccompanyService;
import com.alpha.server.rpc.diagnosis.pojo.*;
import com.alpha.server.rpc.diagnosis.pojo.vo.MedicineQuestionVo;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 伴随症状业务处理类
 */
@Service
public class ConcSymptomServiceImpl implements SymptomAccompanyService {

    @Autowired
    DiagnosisMainsympConcsympDao diagnosisMainsympConcsympDao;
    @Resource
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;
    @Resource(name = "diagnosisQuestionAnswerServiceImpl")
    private AnswerService diagnosisQuestionAnswerService;
    @Resource
    private SyCommonConcSymptomMapper syCommonConcSymptomMapper;

    @Override
    public Map<String, List<MedicineQuestionVo>> mapDiagnosisMainsympConcsymp2(String mainSympCode, List<String> concSympCodes) {
        Map<String, List<MedicineQuestionVo>> dmcsMap = new HashMap<>();
        if (concSympCodes == null || concSympCodes.size() == 0)
            return dmcsMap;
        List<MedicineQuestionVo> dmcs = diagnosisMainsympConcsympDao.listDiagnosisMainsympConcsymp(mainSympCode, concSympCodes);
        for (MedicineQuestionVo dmc : dmcs) {
            String diseaseCode = dmc.getDiseaseCode();
            if(dmcsMap.containsKey(diseaseCode)) {
                List<MedicineQuestionVo> list = dmcsMap.get(diseaseCode);
                list.add(dmc);
            } else {
                List<MedicineQuestionVo> list = new ArrayList<>();
                list.add(dmc);
                dmcsMap.put(diseaseCode, list);
            }
        }
        return dmcsMap;
    }

    @Override
    public DiagnosisMainsympConcsymp getMaxWeightConcSymp(String mainSympCode) {
        return diagnosisMainsympConcsympDao.getMaxWeightConcSymp(mainSympCode);
    }

    @Override
    public List<SyCommonConcSymptom> getSynonymOfCommonConcSymp(List<String> connCodeList) {
        List<SyCommonConcSymptom> syCommonConcSymptomList = syCommonConcSymptomMapper.listByConnCode(connCodeList);
        return syCommonConcSymptomList;
    }

    /**
     * 查询主症状下所有的伴随症状名称，
     *
     * @param mainSympCode
     * @return
     */
    private List<DiagnosisMainsympConcsymp> listConcsymp(String mainSympCode, UserInfo userInfo) {
        List<DiagnosisMainsympConcsymp> dmcs = diagnosisMainsympConcsympDao.listConcsymp(mainSympCode);
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
