package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.exception.ServiceException;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.self.diagnosis.service.UserMedicalRecordService;
import com.alpha.self.diagnosis.utils.MedicineTemplateFactory;
import com.alpha.self.diagnosis.utils.template.Template;
import com.alpha.self.diagnosis.utils.template.WomenTemplate;
import com.alpha.server.rpc.diagnosis.pojo.UserMedicalRecord;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.user.dao.UserBasicRecordDao;
import com.alpha.user.mapper.UserMedicalRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.function.Function;

@Service
public class UserMedicalRecordServiceImpl implements UserMedicalRecordService {

    @Resource
    private UserMedicalRecordMapper userMedicalRecordMapper;
    @Resource
    private UserBasicRecordDao userBasicRecordDao;
    @Resource
    private UserMedicalRecordService userMedicalRecordService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserMedicalRecord build(Long diagnosisId, Function<String, String> allergicHistoryFunction) {
        UserMedicalRecord userMedicalRecord = null;
        UserBasicRecord record = userBasicRecordDao.findByDiagnosisId(diagnosisId);
        if(record == null) {
            throw new ServiceException(ResponseStatus.BASIC_RECORD_NOTFOUND);
        }
        try {
            Template template = MedicineTemplateFactory.build(diagnosisId);
            if(template == null) {
                throw new ServiceException(ResponseStatus.TEMPLATE_NOT_FOUND);
            }
            userMedicalRecord = userMedicalRecordService.getByDiagnosisId(diagnosisId);
            if (userMedicalRecord == null) {
                userMedicalRecord = new UserMedicalRecord();
            }
            if (template instanceof WomenTemplate) {
                WomenTemplate womenTemplate = (WomenTemplate) template;
                userMedicalRecord.setDiagnosisId(diagnosisId);
                userMedicalRecord.setMainSymptom(womenTemplate.getMultiSymptomName());
                userMedicalRecord.setHistoryOfPresent(womenTemplate.getPresenetIllHistory());
                userMedicalRecord.setHistoryOfPast(womenTemplate.getPastIllHistory(allergicHistoryFunction));
                userMedicalRecord.setHistoryOfMenstruation(womenTemplate.getMenstruationHistory());
                userMedicalRecord.setHistoryOfMarriage(womenTemplate.getMarriageHistory());
            } else {
                userMedicalRecord.setDiagnosisId(diagnosisId);
                userMedicalRecord.setMainSymptom(template.getMultiSymptomName());
                userMedicalRecord.setHistoryOfPresent(template.getPresenetIllHistory());
                userMedicalRecord.setHistoryOfPast(template.getPastIllHistory(allergicHistoryFunction));
            }
            this.save(userMedicalRecord);
        } catch (Exception e) {
            logger.error("生成病历发生异常", e);
        }
        return userMedicalRecord;
    }

    @Override
    public UserMedicalRecord getByDiagnosisId(Long diagnosisId) {
        UserMedicalRecord param = new UserMedicalRecord();
        param.setDiagnosisId(diagnosisId);
        UserMedicalRecord userMedicalRecord = userMedicalRecordMapper.selectOne(param);
        return userMedicalRecord;
    }

    @Override
    public void save(UserMedicalRecord userMedicalRecord) {
        Long id = userMedicalRecord.getId();
        if(id == null) {
            userMedicalRecord.setCreateTime(new Date());
            userMedicalRecord.setUpdateTime(new Date());
            userMedicalRecordMapper.insert(userMedicalRecord);
        } else {
            userMedicalRecord.setUpdateTime(new Date());
            userMedicalRecordMapper.updateByPrimaryKey(userMedicalRecord);
        }
    }

    @Override
    public void update(Long diD) {
        UserMedicalRecord userMedicalRecord = new UserMedicalRecord();
        userMedicalRecord.setDiagnosisId(diD);
        userMedicalRecord = userMedicalRecordMapper.selectOne(userMedicalRecord);
        userMedicalRecord.setImportFlag(1);//表示此病历已导入
        userMedicalRecordMapper.updateByPrimaryKey(userMedicalRecord);
    }
}
