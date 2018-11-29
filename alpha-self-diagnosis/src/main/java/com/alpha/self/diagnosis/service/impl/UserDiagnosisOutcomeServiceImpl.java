package com.alpha.self.diagnosis.service.impl;

import com.alpha.self.diagnosis.dao.UserDiagnosisOutcomeDao;
import com.alpha.self.diagnosis.service.UserDiagnosisOutcomeService;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDiagnosisOutcomeServiceImpl implements UserDiagnosisOutcomeService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Resource
    private UserDiagnosisOutcomeDao userDiagnosisOutcomeDao;

    /**
     * 获取前5诊断疾病
     *
     * @param diagnosisId
     * @return
     */
    @Override
    public List<UserDiagnosisOutcome> listTop5UserDiagnosisOutcome(Long diagnosisId, String sympCode) {
        return userDiagnosisOutcomeDao.listTop5UserDiagnosisOutcome(diagnosisId, sympCode);
    }

    /**
     * 确认诊断结果
     */
    public boolean confirmODiagnosisOutcome(Long diagnosisId, String diseaseCode) {
        List<UserDiagnosisOutcome> udos = userDiagnosisOutcomeDao.listUserDiagnosisOutcome(diagnosisId, diseaseCode);
        if (udos == null || udos.size() == 0)
            return false;
        for (UserDiagnosisOutcome udo : udos) {
            udo.setConfirm(1);
            userDiagnosisOutcomeDao.update(udo);
        }
        return true;
    }
}
