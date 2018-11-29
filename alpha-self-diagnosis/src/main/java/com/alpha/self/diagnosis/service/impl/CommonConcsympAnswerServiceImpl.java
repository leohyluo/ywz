package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympConcsympDao;
import com.alpha.self.diagnosis.pojo.vo.BasicAnswerVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.service.AnswerService;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import static java.util.stream.Collectors.*;

/**
 * 常见伴随症状业务处理类
 */
@Service
public class CommonConcsympAnswerServiceImpl implements AnswerService {

    @Resource
    DiagnosisMainsympConcsympDao diagnosisMainsympConcsympDao;

    @Override
    public LinkedHashSet<DiagnosisMainsympConcsymp> get(Long diagnosisId, String mainSympCode, String questionCode, UserInfo userInfo) {
        List<DiagnosisMainsympConcsymp> dmcs = diagnosisMainsympConcsympDao.listConcsymp(mainSympCode);
        dmcs = dmcs.stream().filter(e->e.getTypeFlag() != null).filter(e->e.getTypeFlag().intValue() == 1).collect(toList());
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
        LinkedHashSet<DiagnosisMainsympConcsymp> commonConcsympLinkedHashSet = new LinkedHashSet<>(dmcs);
        return commonConcsympLinkedHashSet;
    }

    @Override
    public LinkedHashSet<IAnswerVo> getAnswerView(Long diagnosisId, String mainSympCode, String questionCode, UserInfo userInfo) {
        LinkedHashSet<DiagnosisMainsympConcsymp> commonConcsympLinkedHashSet = this.get(diagnosisId, mainSympCode, questionCode, userInfo);
        List<DiagnosisMainsympConcsymp> dmcs = commonConcsympLinkedHashSet.stream().filter(e->StringUtils.isNotEmpty(e.getSympName())).collect(toList());

        //"都没有"放最后一位
        Optional<DiagnosisMainsympConcsymp> none = dmcs.stream().filter(e->e.getSympName().equals(GlobalConstants.NONE)).findFirst();
        dmcs = dmcs.stream().filter(e->!e.getSympName().equals(GlobalConstants.NONE)).collect(toList());
        none.ifPresent(dmcs::add);

        List<IAnswerVo> answers = new ArrayList<>();
        for (DiagnosisMainsympConcsymp dmc : dmcs) {
            BasicAnswerVo answer = new BasicAnswerVo(dmc);
            answers.add(answer);
        }
        return new LinkedHashSet<>(answers);
    }
}
