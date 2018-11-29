package com.alpha.self.diagnosis.service.impl;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.OpenDepartmentMapper;
import com.alpha.commons.core.pojo.DepartMent;
import com.alpha.commons.core.pojo.DiagnosisDisease;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.commons.util.StringUtils;
import com.alpha.redis.ObjectsTranscoder;
import com.alpha.redis.RedisMrg;
import com.alpha.self.diagnosis.dao.DataInitDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympQuestionDao;
import com.alpha.self.diagnosis.mapper.PreQuestionAnswerMapper;
import com.alpha.self.diagnosis.mapper.PreQuestionMapper;
import com.alpha.self.diagnosis.service.InitDataService;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympQuestion;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestion;
import com.alpha.server.rpc.diagnosis.pojo.PreQuestionAnswer;
import com.alpha.user.dao.SysConfigDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.*;

/**
 * Created by MR.Wu on 2018-04-09.
 */
@Service
public class InitDataServiceImpl implements InitDataService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataInitDao dataInitDao;
    @Resource
    private DiagnosisMainSymptomsDao diagnosisMainSymptomsDao;

    @Autowired
    private DiagnosisMainsympQuestionDao diagnosisMainsympQuestionDao;

    @Resource
    private OpenDepartmentMapper openDepartmentMapper;

    @Resource
    private PreQuestionMapper preQuestionMapper;

    @Resource
    private PreQuestionAnswerMapper preQuestionAnswerMapper;

    @Resource
    private SysConfigDao sysConfigDao;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;


    @Override
    public void getAllDiagnosisDisease() {
        List<DiagnosisDisease> diagnosisDiseaseList = dataInitDao.getAllDiagnosisDisease();
        logger.info("正在加载疾病数据，总共{}条", diagnosisDiseaseList.size());
        for (DiagnosisDisease diagnosisDisease : diagnosisDiseaseList) {
            String disCode = diagnosisDisease.getDiseaseCode();
            if(null != disCode && !"".equals(disCode))
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(disCode.getBytes(), ObjectsTranscoder.serialize(diagnosisDisease), RedisMrg.DB1);
        }
        logger.info("加载疾病数据完成");
    }

    @Override
    public void getConcSymptom() {
        List<DiagnosisMainSymptoms> mainSymptomsList = diagnosisMainSymptomsDao.listByObjectVersion(1);
        logger.info("开始加载{}个主症状的伴随症状", mainSymptomsList.size());
        for(DiagnosisMainSymptoms dms : mainSymptomsList) {
            String mainSympCode = dms.getSympCode();
            String key = GlobalConstants.REDIS_KEY_MAIN_COCN_PREFIX + mainSympCode;
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).clearList(key.getBytes(), RedisMrg.DB1);

            List<DiagnosisMainsympQuestion> concsympList = diagnosisMainsympQuestionDao.listConcSymptomCount(mainSympCode);
            for(DiagnosisMainsympQuestion item : concsympList) {
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).lpush(key.getBytes(), ObjectsTranscoder.serialize(item), RedisMrg.DB1);
            }
        }
        logger.info("加载伴随症状至redis宗旨");
    }

    @Override
    public void cacheMainsymps() {
        List<DiagnosisMainsympQuestion> dmQuestions = diagnosisMainsympQuestionDao.listDiagnosisMainsympQuestionAll();
        logger.info("正在加载主症状下的所有问题，总共{}条", dmQuestions.size());
        for(DiagnosisMainsympQuestion diagnosisMainsympQuestion : dmQuestions){
            String code = diagnosisMainsympQuestion.getMainSympCode();
            List<DiagnosisMainsympQuestion> diagnosisMainsympQuestionLast = (List<DiagnosisMainsympQuestion>) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(code, RedisMrg.DB2);
            if(null != diagnosisMainsympQuestionLast){
                diagnosisMainsympQuestionLast.add(diagnosisMainsympQuestion);
            }else{
                diagnosisMainsympQuestionLast = new ArrayList<>();
                diagnosisMainsympQuestionLast.add(diagnosisMainsympQuestion);
            }
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(code.getBytes(), ObjectsTranscoder.serialize(diagnosisMainsympQuestionLast), RedisMrg.DB2);
        }
    }

    @Override
    public void cacheOpenDepartment() {
        List<DepartMent> list=openDepartmentMapper.selectAll();
        if(list.size()>0){
          List<String>  listpartment=list.stream().map(DepartMent::getName).collect(Collectors.toList());
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).delKey("partmentlist".getBytes(),13);
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).setSetString(listpartment,"partmentlist",13);
        }
    }

    @Override
    public void cacheSysConfig() {
        List<SysConfig> sysConfigList = sysConfigDao.findAll();
        for (SysConfig item : sysConfigList) {
            String configKey = item.getConfigKey();
            if(StringUtils.isNotEmpty(configKey)) {
                String key = GlobalConstants.REDIS_KEY_SYS_CONFIG.concat("_").concat(configKey);
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(key.getBytes(), ObjectsTranscoder.serialize(item), RedisMrg.DB1);
            }
        }
    }

    @Override
    public void cachePreQuestionAnswer() {
        List<PreQuestionAnswer> preQuestionList = preQuestionAnswerMapper.selectAll();
        Map<String, List<PreQuestionAnswer>> map = preQuestionList.stream().collect(Collectors.groupingBy(PreQuestionAnswer::getQuestionCode));
        map.forEach((k,v) -> {
            String key = GlobalConstants.REDIS_KEY_PRE_QUESTION_ANSWER + k;
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).clearList(key, RedisMrg.DB6);
            for(PreQuestionAnswer preQuestionAnswer : v) {
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).lpush(key, preQuestionAnswer, RedisMrg.DB6);
            }
        });
    }
}
