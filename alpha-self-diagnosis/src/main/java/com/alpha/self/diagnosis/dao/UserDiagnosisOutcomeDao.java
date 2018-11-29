package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.user.pojo.UserDiagnosisOutcome;

import java.util.List;

/**
 * Created by xc.xiong on 2017/9/5.
 */
public interface UserDiagnosisOutcomeDao extends IBaseDao<UserDiagnosisOutcome, Long> {

    /**
     * 查询问题
     *
     * @param diagnosisId
     * @return
     */
    List<UserDiagnosisOutcome> listTop5UserDiagnosisOutcome(Long diagnosisId, String sympCode);


    /**
     * 查询诊断结果
     *
     * @param diagnosisId
     * @return
     */
    List<UserDiagnosisOutcome> listUserDiagnosisOutcome(Long diagnosisId, String diseaseCode);

    /**
     * 删除诊断结果
     * @param diagnosisId
     */
    void deleteByDiagnosisId(Long diagnosisId);

}
