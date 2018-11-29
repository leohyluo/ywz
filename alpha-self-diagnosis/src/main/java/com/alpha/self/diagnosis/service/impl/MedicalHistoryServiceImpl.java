package com.alpha.self.diagnosis.service.impl;

import com.alpha.self.diagnosis.dao.DiagnosisPastmedicalHistoryDao;
import com.alpha.self.diagnosis.service.MedicalHistoryService;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisPastmedicalHistory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 病史业务处理类
 */
@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    @Resource
    private DiagnosisPastmedicalHistoryDao diagnosisPastmedicalHistoryDao;

    @Override
    public List<DiagnosisPastmedicalHistory> listPastMedicalHistoryByMainSympCode(String mainSympCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("sympCode", mainSympCode);
        List<DiagnosisPastmedicalHistory> list = diagnosisPastmedicalHistoryDao.queryPastmedicalHistory(param);
        return Optional.ofNullable(list).orElseGet(ArrayList::new);
    }

    @Override
    public List<DiagnosisPastmedicalHistory> listPastmedicalHistoryByMainSympCodeAndSystemCode(String mainSympCode, String systemCode) {
        return diagnosisPastmedicalHistoryDao.listByMainSympCodeAndSystemCode(mainSympCode, systemCode);
    }
}
