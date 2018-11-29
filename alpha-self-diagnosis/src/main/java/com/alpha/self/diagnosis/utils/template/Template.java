package com.alpha.self.diagnosis.utils.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.DiagnosisSystem;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.YmlUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympNeConcsympDao;
import com.alpha.self.diagnosis.dao.UserDiagnosisDetailDao;
import com.alpha.self.diagnosis.pojo.enums.MainSymptomEnum;
import com.alpha.self.diagnosis.pojo.enums.QuestionEnum;
import com.alpha.self.diagnosis.utils.InstanceUtils;
import com.alpha.self.diagnosis.utils.MedicalRecordUtils;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympNeConcsymp;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;
import com.alpha.user.pojo.DiagnosisMedicalTemplate;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public interface Template {

    public static final Logger logger = LoggerFactory.getLogger(Template.class);

    MainSymptomEnum getMainSymptom();

    /**
     * 获取主症状
     * @return
     */
    String getSymptomName();

    /**
     * 多主症状名字 v1.1.3
     * @return
     */
    String getMultiSymptomName();

    /**
     * 获取现病史
     * @return
     */
    String getPresenetIllHistory();

    /**
     * 获取常见伴随症状引申问题
     * isMainComplaint 是否构成主诉 (构成主诉的要病程，不构成主诉的不需要病程)
     * @return
     */
    String getExtQuestionTextOfCommonConcSymp(boolean isMainComplaint);

    /**
     * 获取既往史
     * @return
     */
    String getPastIllHistory(Function<String, String> allergicHistoryFunction);

    /**
     * 拼装没有引申问题的常见伴随症状、伴随症状、阴性伴随症状
     * @param appType
     * @param diagnosisId
     * @param diagnosisOutcomeList
     * @return
     */
    default String getConcSymptomWithoutExtQuestion(AppType appType, Long diagnosisId, List<UserDiagnosisOutcome> diagnosisOutcomeList) {
        UserDiagnosisDetailDao userDiagnosisDetailDao = SpringContextHolder.getBean("userDiagnosisDetailDaoImpl");
        DiagnosisMainSymptomsDao diagnosisMainSymptomsDao = SpringContextHolder.getBean("diagnosisMainSymptomsDaoImpl");
        DiagnosisMainsympNeConcsympDao diagnosisMainsympNeConcsympDao = SpringContextHolder.getBean("diagnosisMainsympNeConcsympDaoImpl");

        List<UserDiagnosisDetail> allUdds = userDiagnosisDetailDao.listAllUserDiagnosisDetail(diagnosisId);
        List<UserDiagnosisDetail> udds = allUdds.stream().filter(e->e.getQuestionType() != QuestionEnum.附加医学问题.getValue()).collect(toList());
        if(CollectionUtils.isEmpty(udds))
            return null;

        String normalSympContent = "";  //常见伴随症状病历内容
        String concSympContent = "";    //伴随症状病历内容
        String unSympContent = "";      //阴性伴随症状病历内容

        //主症状与常见伴随的依赖关系
        Map<String, Object> param = new HashMap<>();
        String mainSympCode = getMainSymptom().getValue();
        param.put("sympCode", mainSympCode);
        List<DiagnosisMainSymptoms> diagnosisMainSymptomList = diagnosisMainSymptomsDao.query(param);
        DiagnosisMainSymptoms diagnosisMainSymptom = CollectionUtils.isNotEmpty(diagnosisMainSymptomList) ? diagnosisMainSymptomList.get(0) : null;
        if(diagnosisMainSymptom == null) {
            logger.error("根据编码{}未找到对应的主症状", mainSympCode);
            return null;
        }

        Set<String> repliedExtQuestionConcSympSetTmp = new HashSet<>();
        String normalSymptomCodes = diagnosisMainSymptom.getNormalSymptomCode();
        if (StringUtils.isNotEmpty(normalSymptomCodes)) {
            List<String> sympCodeList = Stream.of(normalSymptomCodes.split(",")).collect(toList());
            repliedExtQuestionConcSympSetTmp = diagnosisMainSymptomsDao.listBySympCodeList(sympCodeList).stream().map(DiagnosisMainSymptoms::getSympName).collect(toSet());
        }
        final Set<String> repliedExtQuestionConcSympSet = repliedExtQuestionConcSympSetTmp;

        //书写常见伴随症状
        Optional<String> cocnSympNamesList1 = udds.stream().filter(e -> e.getQuestionType() != null).filter(e->e.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue()).map(UserDiagnosisDetail::getAnswerContent).findFirst();
        String cocnSympNames1 = cocnSympNamesList1.isPresent() ? cocnSympNamesList1.get() : ""; //常见伴随症状
        if(!cocnSympNames1.equals(GlobalConstants.NONE)) {
            StringBuffer subBuffer = new StringBuffer();
            List<String> normalSympNameList = Stream.of(cocnSympNames1.split("、")).collect(toList());
            String concSympNames = normalSympNameList.stream().filter(e -> !repliedExtQuestionConcSympSet.contains(e)).collect(joining("、"));
            if (StringUtils.isNotEmpty(concSympNames)) {
                subBuffer.append("伴").append(concSympNames).append("，");
            }
            normalSympContent = subBuffer.toString();
        }
        //书写伴随症状,由于选择常见伴随症状可能会自动的选择了某些伴随症状（如常见伴随选择发热，那么程序会自动选择了高热、中热、低热这几个伴随症状），所以不能再用AnswerContent字段书写病历
        //Optional<String> cocnSympNamesList2 = udds.stream().filter(e -> e.getQuestionType() != null).filter(e->e.getQuestionType().intValue() == QuestionEnum.伴随症状.getValue().intValue()).map(UserDiagnosisDetail::getAnswerContent).findFirst();
        Optional<String> cocnSympNamesList2 = udds.stream().filter(e -> e.getQuestionType() != null).filter(e->e.getQuestionType().intValue() == QuestionEnum.伴随症状.getValue().intValue()).map(UserDiagnosisDetail::getAnswerJson).findFirst();
        String cocnSympNamesJsonStr = cocnSympNamesList2.isPresent() ? cocnSympNamesList2.get() : ""; //伴随症状
        JSONArray cocnSympJarr = JSONArray.parseArray(cocnSympNamesJsonStr);
        Set<String> cocnSympNameSet = new HashSet<>();
        for (int i = 0; i < cocnSympJarr.size(); i++) {
            JSONObject itemJson = cocnSympJarr.getJSONObject(i);
            String itemSympName = itemJson.getString("answerTitle");
            cocnSympNameSet.add(itemSympName);
        }
        String cocnSympNames2 = cocnSympNameSet.stream().collect(Collectors.joining("、"));
        if(!cocnSympNames2.equals(GlobalConstants.NONE)) {
            StringBuffer subBuffer = new StringBuffer("伴");
            subBuffer.append(cocnSympNames2).append("。");
            concSympContent = subBuffer.toString();
        }
        //常见伴随症状、伴随症状都选择"都没有"
        if (cocnSympNames1.equals(GlobalConstants.NONE) && cocnSympNames2.equals(GlobalConstants.NONE)) {
            StringBuffer sb = new StringBuffer();
            sb.append("无明显其他不适，");
            return sb.toString();
        }
        //书写阴性伴随症状
        List<String> cocnSympNamesList = udds.stream().filter(e -> e.getQuestionType() != null).filter(e -> e.getQuestionType().intValue() == QuestionEnum.伴随症状.getValue().intValue()
                || e.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue()).map(UserDiagnosisDetail::getAnswerContent).collect(Collectors.toList());

        if(CollectionUtils.isNotEmpty(cocnSympNamesList)) {
            String unSelectedCommonConcSymp = "";
            Set<String> cocnSympNames = new HashSet<>();
            for(String itemCocnSympName : cocnSympNamesList) {
                if(StringUtils.isEmpty(itemCocnSympName)) continue;
                cocnSympNames.addAll(Stream.of(itemCocnSympName.split("、")).collect(Collectors.toSet()));
            }
            //找出前5个疾病对应的系统并集
            List<String> systemCodeList = diagnosisOutcomeList.stream().map(UserDiagnosisOutcome::getSystemCode).filter(StringUtils::isNotEmpty).distinct().collect(toList());

            String neSympName = "";
            Set<String> neconcCodeSet = new HashSet<>(); //指定的主症状按科室显示阴性伴随
            neconcCodeSet.add(MainSymptomEnum.咳嗽.getValue());
            neconcCodeSet.add(MainSymptomEnum.皮疹.getValue());
            neconcCodeSet.add(MainSymptomEnum.腹泻.getValue());
            neconcCodeSet.add(MainSymptomEnum.发热.getValue());
            neconcCodeSet.add(MainSymptomEnum.鼻出血.getValue());
            neconcCodeSet.add(MainSymptomEnum.打鼾.getValue());
            neconcCodeSet.add(MainSymptomEnum.鼻塞.getValue());
            neconcCodeSet.add(MainSymptomEnum.耳痛.getValue());
            neconcCodeSet.add(MainSymptomEnum.声音嘶哑.getValue());
            neconcCodeSet.add(MainSymptomEnum.流涕.getValue());
            //妇科
            neconcCodeSet.add(MainSymptomEnum.痛经.getValue());
            neconcCodeSet.add(MainSymptomEnum.外阴瘙痒.getValue());
            neconcCodeSet.add(MainSymptomEnum.阴道流血.getValue());
            neconcCodeSet.add(MainSymptomEnum.下腹部肿块.getValue());
            neconcCodeSet.add(MainSymptomEnum.下腹部疼痛.getValue());
            neconcCodeSet.add(MainSymptomEnum.外阴疼痛.getValue());
            neconcCodeSet.add(MainSymptomEnum.白带异常.getValue());
            neconcCodeSet.add(MainSymptomEnum.月经失调.getValue());

            if(neconcCodeSet.contains(mainSympCode)) {
                Set<String> distinctSympNameSet = new HashSet<>();
                for (String systemCode : systemCodeList) {
                    List<DiagnosisMainsympNeConcsymp> diagnosisMainsympNeConcsympList = diagnosisMainsympNeConcsympDao.listDiagnosisMainsympNeConcsymp(mainSympCode, systemCode);
                    if (CollectionUtils.isNotEmpty(diagnosisMainsympNeConcsympList)) {
                        List<String> itemNeConcList = diagnosisMainsympNeConcsympList.stream().map(DiagnosisMainsympNeConcsymp::getConcSympName)
                                .filter(e -> !cocnSympNames.contains(e)).filter(e -> !distinctSympNameSet.contains(e)).distinct().collect(toList());
                        distinctSympNameSet.addAll(itemNeConcList);
                        if(CollectionUtils.isNotEmpty(itemNeConcList)) {
                            neSympName += "无" + itemNeConcList.stream().collect(Collectors.joining("、")) + "，";
                        }
                    }
                }
                //儿童医院固定加上热咳吐泻
                String hospitalCode = YmlUtils.getValue("hospital.code");
                if ("A001".equalsIgnoreCase(hospitalCode)) {
                    List<DiagnosisMainsympNeConcsymp> diagnosisMainsympNeConcsympList = diagnosisMainsympNeConcsympDao.listByHospitalCodeAndMainSympCode(hospitalCode, mainSympCode);
                    if (CollectionUtils.isNotEmpty(diagnosisMainsympNeConcsympList)) {
                        List<String> defaultNeConcsympList = diagnosisMainsympNeConcsympList.stream().map(DiagnosisMainsympNeConcsymp::getConcSympName)
                                .filter(e -> !cocnSympNames.contains(e)).filter(e -> !distinctSympNameSet.contains(e)).collect(toList());
                        if(CollectionUtils.isNotEmpty(defaultNeConcsympList)) {
                            neSympName += "无" + defaultNeConcsympList.stream().collect(Collectors.joining("、")) + "，";
                        }
                    }
                }
            } else {
                List<DiagnosisMainsympNeConcsymp> diagnosisMainsympNeConcsympList =diagnosisMainsympNeConcsympDao.listByMainSympCode(mainSympCode);
                if (CollectionUtils.isNotEmpty(diagnosisMainsympNeConcsympList)) {
                    List<String> itemNeConcList = diagnosisMainsympNeConcsympList.stream().map(DiagnosisMainsympNeConcsymp::getConcSympName).filter(e -> !cocnSympNames.contains(e))
                            .distinct().collect(toList());
                    if(CollectionUtils.isNotEmpty(itemNeConcList)) {
                        neSympName += "无" + itemNeConcList.stream().collect(Collectors.joining("、")) + "，";
                    }
                }
            }
            /*if(CollectionUtils.isNotEmpty(neConcSympList)) {
                unSelectedCommonConcSymp = neConcSympList.stream().distinct().collect(joining("、"));
                unSympContent = "无" + unSelectedCommonConcSymp + "。";
            }*/
            unSympContent = neSympName;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(normalSympContent);
        sb.append(concSympContent);
        sb.append(unSympContent);
        String concSympText = sb.toString();
        return concSympText;
    }

    default String getConcSymptom2(AppType appType, Long diagnosisId, UserDiagnosisOutcome firstDiagnosis) {
        UserDiagnosisDetailDao userDiagnosisDetailDao = SpringContextHolder.getBean("userDiagnosisDetailDaoImpl");
        DiagnosisMainSymptomsDao diagnosisMainSymptomsDao = SpringContextHolder.getBean("diagnosisMainSymptomsDaoImpl");
        DiagnosisMainsympNeConcsympDao diagnosisMainsympNeConcsympDao = SpringContextHolder.getBean("diagnosisMainsympNeConcsympDaoImpl");

        List<UserDiagnosisDetail> allUdds = userDiagnosisDetailDao.listAllUserDiagnosisDetail(diagnosisId);
        List<UserDiagnosisDetail> udds = allUdds.stream().filter(e->e.getQuestionType() != QuestionEnum.附加医学问题.getValue()).collect(toList());
        if(CollectionUtils.isEmpty(udds))
            return null;

        String normalSympContent = "";  //常见伴随症状病历内容
        String concSympContent = "";    //伴随症状病历内容
        String unSympContent = "";      //阴性伴随症状病历内容
        //书写常见伴随症状
        Optional<String> cocnSympNamesList1 = udds.stream().filter(e -> e.getQuestionType() != null).filter(e->e.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue()).map(UserDiagnosisDetail::getAnswerContent).findFirst();
        String cocnSympNames1 = cocnSympNamesList1.isPresent() ? cocnSympNamesList1.get() : ""; //常见伴随症状
        if(!cocnSympNames1.equals(GlobalConstants.NONE)) {
            StringBuffer sb1 = new StringBuffer();  //有附加问题的常见伴随症状
            StringBuffer sb2 = new StringBuffer();  //无附加问题的常见伴随症状
            List<DiagnosisMainSymptoms> mainSymptomsList;
            if(appType == AppType.WOMAN) {
                mainSymptomsList = diagnosisMainSymptomsDao.listByObjectVersion(2);
            } else {
                mainSymptomsList = diagnosisMainSymptomsDao.listByObjectVersion(1);
            }
            Map<String, DiagnosisMainSymptoms> mainSymptomMap = mainSymptomsList.stream().collect(Collectors.toMap(DiagnosisMainSymptoms::getSympName, Function.identity()));

            StringBuffer subBuffer = new StringBuffer("伴");
            List<String> normalSympNameList = Stream.of(cocnSympNames1.split("、")).collect(toList());
            for(String normalSympName : normalSympNameList) {
                if(mainSymptomMap.containsKey(normalSympName)) {
                    DiagnosisMainSymptoms mainSymptom = mainSymptomMap.get(normalSympName);
                    //已回答的附加问题的答案
                    List<UserDiagnosisDetail> additionalUdds = allUdds.stream().filter(e->e.getQuestionType() != null).filter(e->e.getQuestionType().intValue() == QuestionEnum.附加医学问题.getValue().intValue()).filter(e->e.getSympCode().equals(mainSymptom.getSympCode())).collect(toList());
                    if(CollectionUtils.isNotEmpty(additionalUdds)) {
                        Map<String, List<UserDiagnosisDetail>> additionalMap = additionalUdds.stream().collect(Collectors.groupingBy(UserDiagnosisDetail::getSympCode));
                        //已选择的常见伴随症状如果有附加问题，则将附加问题的答案添加到病历
                        for(Map.Entry<String, List<UserDiagnosisDetail>> item : additionalMap.entrySet()) {
                            String itemSympCode = item.getKey();
                            Map<String, String> answerMap = Optional.ofNullable(additionalMap.get(itemSympCode)).orElse(Lists.newArrayList()).stream().collect(Collectors.toMap(UserDiagnosisDetail::getQuestionCode, UserDiagnosisDetail::getAnswerContent));
                            String itemAdditionalContent = UserDiagnosisDetail.getAdditionQuestionComment(itemSympCode, answerMap);
                            sb1.append(itemAdditionalContent);
                        }
                    } else {
                        sb2.append(normalSympName).append("，");
                    }
                } else {
                    sb2.append(normalSympName).append("，");
                }
            }
            normalSympContent = subBuffer.append(sb1.toString()).append(sb2.toString()).toString();
        }
        //书写伴随症状
        Optional<String> cocnSympNamesList2 = udds.stream().filter(e -> e.getQuestionType() != null).filter(e->e.getQuestionType().intValue() == QuestionEnum.伴随症状.getValue().intValue()).map(UserDiagnosisDetail::getAnswerContent).findFirst();
        String cocnSympNames2 = cocnSympNamesList2.isPresent() ? cocnSympNamesList2.get() : ""; //伴随症状
        if(!cocnSympNames2.equals(GlobalConstants.NONE)) {
            StringBuffer subBuffer = new StringBuffer("伴");
            subBuffer.append(cocnSympNames2).append("。");
            concSympContent = subBuffer.toString();
        }
        //常见伴随症状、伴随症状都选择"都没有"
        if (cocnSympNames1.equals(GlobalConstants.NONE) && cocnSympNames2.equals(GlobalConstants.NONE)) {
            StringBuffer sb = new StringBuffer();
            sb.append("无明显其他不适，");
            return sb.toString();
        }
        //书写阴性伴随症状
        List<String> cocnSympNamesList = udds.stream().filter(e -> e.getQuestionType() != null).filter(e -> e.getQuestionType().intValue() == QuestionEnum.伴随症状.getValue().intValue()
                || e.getQuestionType().intValue() == QuestionEnum.常见伴随症状.getValue().intValue()).map(UserDiagnosisDetail::getAnswerContent).collect(Collectors.toList());

        if(CollectionUtils.isNotEmpty(cocnSympNamesList)) {
            String unSelectedCommonConcSymp = "";
            Set<String> cocnSympNames = new HashSet<>();
            for(String itemCocnSympName : cocnSympNamesList) {
                if(StringUtils.isEmpty(itemCocnSympName)) continue;
                cocnSympNames.addAll(Stream.of(itemCocnSympName.split("、")).collect(Collectors.toSet()));
            }
            String mainSympCode = udds.stream().filter(e->e.getQuestionType().intValue() == QuestionEnum.主症状.getValue().intValue()).map(UserDiagnosisDetail::getSympCode).findFirst().get();
            String systemCode = firstDiagnosis == null ? null : firstDiagnosis.getSystemCode();
            List<DiagnosisMainsympNeConcsymp> diagnosisMainsympNeConcsympList = diagnosisMainsympNeConcsympDao.listDiagnosisMainsympNeConcsymp(mainSympCode, systemCode);
            if(CollectionUtils.isNotEmpty(diagnosisMainsympNeConcsympList)) {
                unSelectedCommonConcSymp = diagnosisMainsympNeConcsympList.stream().map(DiagnosisMainsympNeConcsymp::getConcSympName).filter(e->!cocnSympNames.contains(e))
                        .collect(Collectors.joining("、"));
            }
            if(StringUtils.isNotEmpty(unSelectedCommonConcSymp)) {
                unSympContent = "无" + unSelectedCommonConcSymp + "。";
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append(normalSympContent);
        sb.append(concSympContent);
        sb.append(unSympContent);
        String concSympText = sb.toString();
        return concSympText;
    }

    default String getGeneralSymptom(UserBasicRecord userBasicRecord) {
        StringBuffer strBuff = new StringBuffer();
        if(userBasicRecord != null) {
            String shit = userBasicRecord.getShit() == null ? "" : userBasicRecord.getShit();
            String urine = userBasicRecord.getUrine() == null ? "" : userBasicRecord.getUrine();
            String appetite = userBasicRecord.getAppetite() == null ? "" : userBasicRecord.getAppetite();
            String menstrualPeriod = userBasicRecord.getMentality() == null ? "" : userBasicRecord.getMentality();


            String menstrualPeriod2 = "好".equals(menstrualPeriod) ? "精神可" : "";
            menstrualPeriod2 = "差".equals(menstrualPeriod) ? "精神不佳" : menstrualPeriod2;
            menstrualPeriod2 = "一般".equals(menstrualPeriod) ? "精神一般" : menstrualPeriod2;


            String appetite2 = "好".equals(appetite) ? "食欲可" : "";
            appetite2 = "差".equals(appetite) ? "纳差" : appetite2;
            appetite2 = "一般".equals(appetite) ? "食欲一般" : appetite2;


            String str1 = "好".equals(menstrualPeriod) && "好".equals(appetite) ? "精神食欲可" : "";
            if("".equals(str1)) {
                if(!"".equals(menstrualPeriod2) && !"".equals(appetite2)) {
                    str1 = menstrualPeriod2 + "," + appetite2;
                } else if (!"".equals(menstrualPeriod2) && "".equals(appetite2)) {
                    str1 = menstrualPeriod2;
                } else if ("".equals(menstrualPeriod2) && !"".equals(appetite2)) {
                    str1 = appetite2;
                }
            }

            String shit2 = "正常".equals(shit) ? "大便正常" : "";
            shit2 = "不成形".equals(shit) ? "大便不成形" : shit2;
            shit2 = "干燥".equals(shit) ? "大便硬结" : shit2;

            String urine2 = "正常".equals(urine) ? "小便正常" : "";
            urine2 = "减少".equals(urine) ? "小便尿量减少" : urine2;
            urine2 = "增多".equals(urine) ? "小便尿量增多" : urine2;

            String str2 = "正常".equals(shit) && "正常".equals(urine) ? "二便正常" :  "";
            if("".equals(str2)) {
                if(!"".equals(shit2) && !"".equals(urine2)) {
                    str2 = shit2 + "，" + urine2;
                } else if (!"".equals(shit2) && "".equals(urine2)) {
                    str2 = shit2;
                } else if ("".equals(shit2) && !"".equals(urine2)) {
                    str2 = urine2;
                }
            }

            if(!"".equals(str1) && !"".equals(str2)) {
                strBuff.append(str1).append("，").append(str2).append("。");
            } else if(!"".equals(str1) && "".equals(str2)) {
                strBuff.append(str1).append("。");
            } else if("".equals(str1) && !"".equals(str2)) {
                strBuff.append(str2).append("。");
            }
        }
        return  strBuff.toString();
    }
}
