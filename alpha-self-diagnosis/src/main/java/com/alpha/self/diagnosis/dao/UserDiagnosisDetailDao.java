package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.user.pojo.UserDiagnosisDetail;

import java.util.List;

/**
 * Created by xc.xiong on 2017/9/5.
 */
public interface UserDiagnosisDetailDao extends IBaseDao<UserDiagnosisDetail, Long> {

    /**
     * 查询问题
     *
     * @param diagnosisId
     * @param questionCode
     * @return
     */
    UserDiagnosisDetail getUserDiagnosisDetail(Long diagnosisId, String questionCode);

    /**
     * 查询问诊过程（不包括附加问题）
     *
     * @param diagnosisId
     * @return
     */
    List<UserDiagnosisDetail> listUserDiagnosisDetail(Long diagnosisId);

    /**
     * 查询问诊过程（包括附加问题）
     * @param diagnosisId
     * @return
     */
    List<UserDiagnosisDetail> listAllUserDiagnosisDetail(Long diagnosisId);

    /**
     * 根据就诊编号与主症状编码查询已回答的问题
     * @param
     * @return
     */
    List<UserDiagnosisDetail> listUserDiagnosisDetail(Long diagnosisId, String sympCode);
    
    /**
     * 根据就诊编号与问题编码查询已回答的问题
     * @param
     * @return
     */
    List<UserDiagnosisDetail> listUserDiagnosisDetail(Long diagnosisId, List<String> questionCodeList);
    
    /**
     * 根据问诊编号，主症状编码删除已回答的问题
     * @param diagnosisId
     * @param sympCode
     */
    void deleteUserDiagnosisDetail(Long diagnosisId, String sympCode);

    /**
     * 删除附加问题
     * @param diagnosisId
     */
    void deleteAdditonalUserDiagnosisDetail(Long diagnosisId);

    void deleteAdditonalUserDiagnosisDetailById(Long diagnosisId, Long id);
}
