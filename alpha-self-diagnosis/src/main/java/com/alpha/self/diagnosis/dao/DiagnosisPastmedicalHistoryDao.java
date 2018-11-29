package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.self.diagnosis.pojo.vo.DiseaseVo;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisPastmedicalHistory;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisSubpastmedicalHistory;

import java.util.List;
import java.util.Map;

public interface DiagnosisPastmedicalHistoryDao extends IBaseDao<DiagnosisPastmedicalHistory, Long> {

    public List<DiagnosisPastmedicalHistory> queryPastmedicalHistory(Map<String, Object> param);

    public List<DiagnosisSubpastmedicalHistory> querySubPastmedicalHistory(Map<String, Object> param);

    /**
     * 查询已选择的既往史
     *
     * @param param
     * @return
     */
    List<DiseaseVo> querySelectedPastmedicalHistory(Map<String, Object> param);

    /**
     * 更新既往史用户频次
     *
     * @param param
     */
    void updateUserSelectCount(Map<String, Object> param);

    public List<DiagnosisPastmedicalHistory> searchPastmedicalHistory(Map<String, Object> param);

    /**
     * 根据主症状与疾病编码查询既往史
     * @param mainSympCode
     * @param systemCode
     * @return
     */
    List<DiagnosisPastmedicalHistory> listByMainSympCodeAndSystemCode(String mainSympCode, String systemCode);
}
