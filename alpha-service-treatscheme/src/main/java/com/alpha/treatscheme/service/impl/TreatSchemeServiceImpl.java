package com.alpha.treatscheme.service.impl;

import com.alpha.commons.core.pojo.DiagnosisDisease;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisDiseaseSign;
import com.alpha.treatscheme.dao.*;
import com.alpha.treatscheme.pojo.DiagnosisDiseaseCheck;
import com.alpha.treatscheme.pojo.DiagnosisDiseasePhysicalexam;
import com.alpha.treatscheme.pojo.DiagnosisDiseaseTreatoptions;
import com.alpha.treatscheme.pojo.vo.TreatSchemeVo;
import com.alpha.treatscheme.service.TreatSchemeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TreatSchemeServiceImpl implements TreatSchemeService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisDiseaseCheckDao diagnosisDiseaseCheckDao;

    @Autowired
    private DiagnosisDiseasePhysicalexamDao diagnosisDiseasePhysicalexamDao;

    @Autowired
    private DiagnosisDiseaseTreatoptionsDao diagnosisDiseaseTreatoptionsDao;

    @Autowired
    private DiagnosisDiseaseDao diagnosisDiseaseDao;

    @Resource
    private DiagnosisDiseaseSignDao diagnosisDiseaseSignDao;

    /**
     * 获取治疗方案
     *
     * @param diseaseCode
     */
    public TreatSchemeVo getTreatScheme(String diseaseCode) {
        if (StringUtils.isEmpty(diseaseCode)) {
            return null;
        }
        DiagnosisDisease disease = diagnosisDiseaseDao.getDiagnosisDisease(diseaseCode);
        if (disease == null) {
            return null;
        }
        List<DiagnosisDiseaseTreatoptions> diagnosisDiseaseTreatoptions = diagnosisDiseaseTreatoptionsDao.getDiagnosisDiseaseTreatoptions(diseaseCode);
        /*if (diagnosisDiseaseTreatoptions == null) {
            return null;
        }*/
        List<DiagnosisDiseaseSign> diagnosisDiseaseSigns = diagnosisDiseaseSignDao.listByDiseaseCode(diseaseCode);
        List<DiagnosisDiseaseCheck> diagnosisDiseaseChecks = diagnosisDiseaseCheckDao.listDiagnosisDiseaseCheck(diseaseCode);
        List<DiagnosisDiseasePhysicalexam> diagnosisDiseasePhysicalexams = diagnosisDiseasePhysicalexamDao.listDiagnosisDiseasePhysicalexam(diseaseCode);
        return packgeTreatScheme(disease, diagnosisDiseaseTreatoptions, diagnosisDiseaseChecks, diagnosisDiseasePhysicalexams, diagnosisDiseaseSigns);

    }

    /**
     * 获取治疗方案相关内容
     *
     * @param disease
     * @param diagnosisDiseaseTreatoptions
     * @param diagnosisDiseaseChecks
     * @param diagnosisDiseasePhysicalexams
     */
    public TreatSchemeVo packgeTreatScheme(DiagnosisDisease disease, List<DiagnosisDiseaseTreatoptions> diagnosisDiseaseTreatoptions,
                                           List<DiagnosisDiseaseCheck> diagnosisDiseaseChecks, List<DiagnosisDiseasePhysicalexam> diagnosisDiseasePhysicalexams,
                                           List<DiagnosisDiseaseSign> diagnosisDiseaseSigns) {
        TreatSchemeVo treatSchemeVo = new TreatSchemeVo();
        try {
            treatSchemeVo.setDiseaseCode(disease.getDiseaseCode());
            treatSchemeVo.setDiseaseName(disease.getDiseaseName());
            treatSchemeVo.setDiseaseDefinition(disease.getDefinition());
            //treatSchemeVo.setSchemeCode(diagnosisDiseaseTreatoptions.getOptionCode());
            //treatSchemeVo.setSchemeName(diagnosisDiseaseTreatoptions.getOptionName());
            //treatSchemeVo.setSchemeContent(diagnosisDiseaseTreatoptions.getOptionContent());
            StringBuilder sb = new StringBuilder();
            if(CollectionUtils.isNotEmpty(diagnosisDiseaseTreatoptions)) {
                int level1Index = 1;
                List<String> optionNameList = diagnosisDiseaseTreatoptions.stream().filter(e->StringUtils.isNotEmpty(e.getOptionName())).map(DiagnosisDiseaseTreatoptions::getOptionName).distinct().collect(Collectors.toList());
                for(String optionName : optionNameList) {
                    List<DiagnosisDiseaseTreatoptions> subTreatoptions = diagnosisDiseaseTreatoptions.stream().filter(e->StringUtils.isNotEmpty(e.getOptionName()))
                            .filter(e->e.getOptionName().equals(optionName)).peek(e->e.setDefaultOrder(e.getDefaultOrder() == null ? 1 : e.getDefaultOrder()))
                            .sorted(Comparator.comparing(DiagnosisDiseaseTreatoptions::getDefaultOrder)).collect(Collectors.toList());
                    if(optionNameList.size() == 1) {
                        sb.append(optionName).append(":");
                    } else {
                        String br = subTreatoptions.size() > 1 ? "<br>" : "";
                        sb.append("【").append(level1Index).append("】").append(optionName).append(":").append(br);
                    }
                    String optionName2Text = buildOption2Content(level1Index, subTreatoptions);
                    sb.append(optionName2Text);
                    sb.append("<br>");
                    level1Index++;
                }
                treatSchemeVo.setSchemeContent(sb.toString());
            }
            List<TreatSchemeVo.Check> checks = new ArrayList<>();
            List<TreatSchemeVo.Physicalexam> physicalexams = new ArrayList<>();
            for (DiagnosisDiseaseCheck ddc : diagnosisDiseaseChecks) {
                TreatSchemeVo.Check check = new TreatSchemeVo.Check();
                check.setCheckCode(ddc.getCheckCode());
                check.setCheckName(ddc.getCheckName());
                checks.add(check);
            }
            for (DiagnosisDiseasePhysicalexam ddp : diagnosisDiseasePhysicalexams) {
                TreatSchemeVo.Physicalexam physicalexam = new TreatSchemeVo.Physicalexam();
                physicalexam.setExamCode(ddp.getExamCode());
                physicalexam.setExamName(ddp.getExamName());
                physicalexams.add(physicalexam);
            }
            List<TreatSchemeVo.DiseaseSign> signs = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(diagnosisDiseaseSigns)) {
                for(DiagnosisDiseaseSign dds : diagnosisDiseaseSigns) {
                    TreatSchemeVo.DiseaseSign diseaseSign = new TreatSchemeVo.DiseaseSign();
                    diseaseSign.setSignCode(dds.getSignCode());
                    diseaseSign.setSignName(dds.getSignName());
                    signs.add(diseaseSign);
                }
            }
            treatSchemeVo.setChecks(checks);
            treatSchemeVo.setPhysicalexams(physicalexams);
            treatSchemeVo.setSigns(signs);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return treatSchemeVo;
    }

    private String buildOption2Content(int parentIndex, List<DiagnosisDiseaseTreatoptions> optionName2List) {
        StringBuilder sb = new StringBuilder();

        List<String> optionName2s = optionName2List.stream().map(DiagnosisDiseaseTreatoptions::getOptionName2).distinct().collect(Collectors.toList());
        int level2Index = 1;
        for(String optionName2 : optionName2s) {
            if(StringUtils.isEmpty(optionName2)) {
                List<DiagnosisDiseaseTreatoptions> itemOptionName2List = optionName2List.stream().filter(e->StringUtils.isEmpty(e.getOptionName2()))
                        .sorted(Comparator.comparing(DiagnosisDiseaseTreatoptions::getDefaultOrder)).collect(Collectors.toList());
                if(itemOptionName2List.size() == 1) {
                    DiagnosisDiseaseTreatoptions ddt = itemOptionName2List.get(0);
                    String optionContent = ddt.getOptionContent();
                    if(StringUtils.isNotEmpty(optionContent)) {
                        sb.append(optionContent);
                    }
                } else {
                    for(DiagnosisDiseaseTreatoptions ddt : itemOptionName2List) {
                        String level2OrderNo = String.valueOf(parentIndex).concat(".").concat(String.valueOf(level2Index));
                        String optionContent = ddt.getOptionContent();
                        if(StringUtils.isNotEmpty(optionContent)) {
                            sb.append(level2OrderNo).append(optionContent).append("<br>");
                            if (level2Index < itemOptionName2List.size())
                                level2Index++;
                        }
                    }
                }
            } else {
                List<DiagnosisDiseaseTreatoptions> itemOptionName2List = optionName2List.stream().filter(e->StringUtils.isNotEmpty(e.getOptionName2()))
                        .filter(e->e.getOptionName2().equals(optionName2)).sorted(Comparator.comparing(DiagnosisDiseaseTreatoptions::getDefaultOrder)).collect(Collectors.toList());

                String level2OrderNo = String.valueOf(parentIndex).concat(".").concat(String.valueOf(level2Index));
                //String maohao = itemOptionName2List.size() > 1 ? ":" : "";
                String br = itemOptionName2List.size() > 1 ? "<br>" : "";
                sb.append(level2OrderNo).append(optionName2).append(":").append(br);

                String optionContent = "";
                if(itemOptionName2List.size() == 1) {
                    DiagnosisDiseaseTreatoptions ddt = itemOptionName2List.get(0);
                    optionContent = ddt.getOptionContent();
                    if(StringUtils.isNotEmpty(optionContent)) {
                        sb.append(ddt.getOptionContent()).append("<br>");
                    }
                } else {
                    int level2Index2 = 1;
                    for(DiagnosisDiseaseTreatoptions ddt : itemOptionName2List) {
                        optionContent = ddt.getOptionContent();
                        if(StringUtils.isNotEmpty(optionContent)) {
                            String level2OrderNo2 = "(" + level2Index2 + ")";
                            sb.append(level2OrderNo2).append(optionContent).append("<br>");
                            level2Index2++;
                        }
                    }
                }
            }
            level2Index++;
        }
        return sb.toString();
    }

}
