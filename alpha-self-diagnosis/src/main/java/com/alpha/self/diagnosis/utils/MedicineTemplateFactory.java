package com.alpha.self.diagnosis.utils;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisOutcomeDao;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.self.diagnosis.service.UserDiagnosisOutcomeService;
import com.alpha.self.diagnosis.utils.template.Template;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.user.service.UserBasicRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;

import static java.util.stream.Collectors.*;

public class MedicineTemplateFactory {

    private static Logger logger = LoggerFactory.getLogger(MedicineTemplateFactory.class);

    public static Template build(Long diagnosisId) throws Exception {
        UserBasicRecordService userBasicRecordService = SpringContextHolder.getBean("userBasicRecordServiceImpl");
        UserDiagnosisDetailDao userDiagnosisDetailDao = SpringContextHolder.getBean("userDiagnosisDetailDaoImpl");
        DiagnosisMainSymptomsDao diagnosisMainSymptomsDao = SpringContextHolder.getBean("diagnosisMainSymptomsDaoImpl");
        UserDiagnosisOutcomeDao userDiagnosisOutcomeDao = SpringContextHolder.getBean("userDiagnosisOutcomeDaoImpl");

        UserBasicRecord userBasicRecord = userBasicRecordService.findByDiagnosisId(diagnosisId);
        List<UserDiagnosisDetail> userDiagnosisDetailList = userDiagnosisDetailDao.listAllUserDiagnosisDetail(diagnosisId);
        Map<String, Object> param = new HashMap<>();
        param.put("sympCode", userBasicRecord.getMainSymptomCode());
        List<DiagnosisMainSymptoms> diagnosisMainSymptomsList0 = diagnosisMainSymptomsDao.query(param);
        if(CollectionUtils.isEmpty(diagnosisMainSymptomsList0))
            return null;
        DiagnosisMainSymptoms mainSymptoms = diagnosisMainSymptomsList0.get(0);

        Template medicalTemplate = null;
        Map<String, String> question = userDiagnosisDetailList.stream().collect(toMap(UserDiagnosisDetail::getQuestionCode, UserDiagnosisDetail::getAnswerContent, (e1,e2)->e2));
        MainSymptomEnum mainSymptomEnum = MainSymptomEnum.findByValue(mainSymptoms.getSympCode());
        if(mainSymptomEnum == null) return null;

        //UserDiagnosisOutcome firstDiagnosis = userDiagnosisOutcomeDao.getDiagnosisSystem4FirstDisese(diagnosisId, userBasicRecord.getMainSymptomCode());
        List<UserDiagnosisOutcome> diagnosisOutcomeList = userDiagnosisOutcomeDao.listTop5UserDiagnosisOutcome(diagnosisId, userBasicRecord.getMainSymptomCode());
        List<Template> templateList = InstanceUtils.getAllInstances(Template.class);
        List<Template> mainTemplateList = templateList.stream().filter(e->mainSymptomEnum == e.getMainSymptom()).collect(toList());

        if (CollectionUtils.isNotEmpty(diagnosisOutcomeList)) {
            Class clazz = mainTemplateList.get(0).getClass();
            Constructor constructor = clazz.getConstructor(UserBasicRecord.class, Map.class, List.class);
            Template obj = (Template) constructor.newInstance(userBasicRecord, question, diagnosisOutcomeList);
            medicalTemplate = obj;
        } else {
            //与诊断无关的病历模板
            Class clazz = mainTemplateList.get(0).getClass();
            Constructor constructor = clazz.getConstructor(UserBasicRecord.class, Map.class);
            Template obj = (Template) constructor.newInstance(userBasicRecord, question);
            medicalTemplate = obj;
        }
        return medicalTemplate;
    }

    /**
     * 获取多主症状病历模板
     * repliedConstructSympList 多主症状列表
     * @return
     */
    public static List<Template> getMultiMainSymptom(Long diagnosisId, Map<String, String> question, List<DiagnosisMainSymptoms> repliedConstructSympList) throws Exception{
        UserBasicRecordService userBasicRecordService = SpringContextHolder.getBean("userBasicRecordServiceImpl");
        DiagnosisMainSymptomsDao diagnosisMainSymptomsDao = SpringContextHolder.getBean("diagnosisMainSymptomsDaoImpl");
        UserDiagnosisOutcomeService userDiagnosisOutcomeService = SpringContextHolder.getBean("userDiagnosisOutcomeServiceImpl");

        UserBasicRecord userBasicRecord = userBasicRecordService.findByDiagnosisId(diagnosisId);
        String mainSympCode = userBasicRecord.getMainSymptomCode();
        List<UserDiagnosisOutcome> top5DiseaseList = userDiagnosisOutcomeService.listTop5UserDiagnosisOutcome(diagnosisId, mainSympCode);
        //第一诊断对应的疾病系统
        String systemCodeOfFirstDiagnosis = null;
        if (CollectionUtils.isNotEmpty(top5DiseaseList)) {
            UserDiagnosisOutcome firstDiagnosis = top5DiseaseList.get(0);
            systemCodeOfFirstDiagnosis = firstDiagnosis.getSystemCode();
            logger.info("第一诊断{}对应的疾病系统是{}", firstDiagnosis.getDiseaseName(), systemCodeOfFirstDiagnosis);
        } else {
            logger.info("无诊断结果");
        }
        if (StringUtils.isNotEmpty(systemCodeOfFirstDiagnosis)) {
            //找出疾病系统下会构成多主症状的常见伴随症状
            List<DiagnosisMainSymptoms> repliedCommonSymptomList = diagnosisMainSymptomsDao.listMainSymptomOfExtQuestion(diagnosisId.toString());     //问诊过程中回答了有引申问题的常见伴随症状
            //已回答的常见伴随症状是否会构成多主症状
            if (CollectionUtils.isNotEmpty(repliedCommonSymptomList)) {
                List<DiagnosisMainSymptoms> constructMainSymptomList = diagnosisMainSymptomsDao.listConstructSympOfMainSymptom(mainSympCode, systemCodeOfFirstDiagnosis); //构成主诉的常见伴随症状
                if (CollectionUtils.isNotEmpty(constructMainSymptomList)) {
                    List<String> constructMainSymptomCodeList = constructMainSymptomList.stream().map(DiagnosisMainSymptoms::getSympCode).collect(toList());
                    //多个常见伴随症状构成主症状则取defaultOrder最小的
                    for (String sympCode : constructMainSymptomCodeList) {
                        Optional<DiagnosisMainSymptoms> optionalDiagnosisMainSymptoms = repliedCommonSymptomList.stream().filter(e -> sympCode.contains(e.getSympCode())).findFirst();
                        if (optionalDiagnosisMainSymptoms.isPresent()) {
                            repliedConstructSympList.add(optionalDiagnosisMainSymptoms.get());
                            break;
                        }
                    }
                }
            }
        }
        //将主症状、会构成多主症状的常见伴随症状放一起，然后按病程倒序排列
        Map<String,Object> param = new HashMap<>();
        param.put("sympCode", mainSympCode);
        DiagnosisMainSymptoms mainSymptoms = diagnosisMainSymptomsDao.query(param).get(0);
        repliedConstructSympList.add(mainSymptoms);
        //按病程倒序排列
        if (repliedConstructSympList.size() > 1) {
            UserDiagnosisDetailDao userDiagnosisDetailDao = SpringContextHolder.getBean("userDiagnosisDetailDaoImpl");
            List<UserDiagnosisDetail> userDiagnosisDetailList = userDiagnosisDetailDao.listAllUserDiagnosisDetail(diagnosisId);
            repliedConstructSympList = MedicalRecordUtils.sortMultiMainSymptoms(repliedConstructSympList,userDiagnosisDetailList);
        }
        //通过反射构建主症状病历模板生成主症状
        List<Template> templateObjectList = new ArrayList<>();
        List<Template> templateList = InstanceUtils.getAllInstances(Template.class);
        for (DiagnosisMainSymptoms dms : repliedConstructSympList) {
            for (Template template : templateList) {
                if (template.getMainSymptom().getValue().equals(dms.getSympCode())) {
                    //与诊断无关的病历模板
                    Class clazz = template.getClass();
                    Constructor constructor = clazz.getConstructor(UserBasicRecord.class, Map.class);
                    Template obj = (Template) constructor.newInstance(userBasicRecord, question);
                    templateObjectList.add(obj);
                }
            }
        }
        return templateObjectList;
    }

    /**
     * 获取回答了引申问题的常见伴随症状（不包括已构成多主症状的常见伴随症状）
     * constructMainSymptomList 已构成多主诉的常见伴随症状对应的主症状
     * @return
     */
    public static List<Template> getRepliedCommonConcSympWithoutMultiMainSymptom(Long diagnosisId, Map<String, String> question, List<DiagnosisMainSymptoms> constructMainSymptomList) throws Exception{
        List<Template> templateObjectList = new ArrayList<>();
        DiagnosisMainSymptomsDao diagnosisMainSymptomsDao = SpringContextHolder.getBean("diagnosisMainSymptomsDaoImpl");
        List<DiagnosisMainSymptoms> repliedCommonSymptomList = diagnosisMainSymptomsDao.listMainSymptomOfExtQuestion(diagnosisId.toString());     //问诊过程中回答了有引申问题的常见伴随症状
        if(CollectionUtils.isEmpty(repliedCommonSymptomList)) {
            return templateObjectList;
        }
        UserBasicRecordService userBasicRecordService = SpringContextHolder.getBean("userBasicRecordServiceImpl");
        UserBasicRecord userBasicRecord = userBasicRecordService.findByDiagnosisId(diagnosisId);
        String mainSympCode = userBasicRecord.getMainSymptomCode();
        //已回答的常见伴随症状是否会构成多主症状
        List<DiagnosisMainSymptoms> repliedConstructSympList = new ArrayList<>();
        //List<DiagnosisMainSymptoms> constructMainSymptomList = diagnosisMainSymptomsDao.listConstructSympOfMainSymptom(mainSympCode); //构成主诉的常见伴随症状
        if (CollectionUtils.isNotEmpty(constructMainSymptomList)) {
            Set<String> constructMainSymptomCodeSet = constructMainSymptomList.stream().map(DiagnosisMainSymptoms::getSympCode).collect(toSet());
            repliedConstructSympList = repliedCommonSymptomList.stream().filter(e->!constructMainSymptomCodeSet.contains(e.getSympCode())).collect(toList());
        } else {
            repliedConstructSympList = repliedCommonSymptomList;
        }
        //通过反射构建主症状病历模板生成主症状
        List<Template> templateList = InstanceUtils.getAllInstances(Template.class);
        for (DiagnosisMainSymptoms dms : repliedConstructSympList) {
            for (Template template : templateList) {
                if (template.getMainSymptom().getValue().equals(dms.getSympCode())) {
                    //与诊断无关的病历模板
                    Class clazz = template.getClass();
                    Constructor constructor = clazz.getConstructor(UserBasicRecord.class, Map.class);
                    Template obj = (Template) constructor.newInstance(userBasicRecord, question);
                    templateObjectList.add(obj);
                }
            }
        }
        return templateObjectList;
    }
}
