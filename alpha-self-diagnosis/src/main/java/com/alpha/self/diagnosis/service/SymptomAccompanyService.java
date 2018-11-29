package com.alpha.self.diagnosis.service;

import com.alpha.self.diagnosis.pojo.vo.BasicQuestionVo;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp;
import com.alpha.server.rpc.diagnosis.pojo.SyCommonConcSymptom;
import com.alpha.server.rpc.diagnosis.pojo.vo.MedicineQuestionVo;
import com.alpha.server.rpc.user.pojo.UserInfo;

import java.util.*;

/**
 * Created by xc.xiong on 2017/9/11.
 */
public interface SymptomAccompanyService {

    /**
     * 查询疾病下的伴随症状及标准差、权重
     * @param mainSympCode
     * @param concSympCodes
     * @return
     */
    Map<String, List<MedicineQuestionVo>> mapDiagnosisMainsympConcsymp2(String mainSympCode, List<String> concSympCodes);

    /**
     * 查询主症状下权重最高的伴随症状
     * @param mainSympCode
     * @return
     */
    DiagnosisMainsympConcsymp getMaxWeightConcSymp(String mainSympCode);

    /**
     * 获取常见伴随症状同义词
     * @param commonSympCodeList
     * @return
     */
    List<SyCommonConcSymptom> getSynonymOfCommonConcSymp(List<String> commonSympCodeList);
}
