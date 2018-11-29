package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.self.diagnosis.pojo.vo.DiseaseVo;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisAllergicHistory;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisSuballergicHistory;

import java.util.List;
import java.util.Map;

public interface DiagnosisAllergicHistoryDao extends IBaseDao<DiagnosisAllergicHistory, Long> {

    List<DiagnosisAllergicHistory> queryAllergicHistory(Map<String, Object> param);

    List<DiagnosisSuballergicHistory> querySubAllergicHistory(Map<String, Object> param);

    /**
     * 查询已选择的过敏史
     *
     * @param param
     * @return
     */
    List<DiseaseVo> querySelectedAllergicHistory(Map<String, Object> param);

    /**
     * 更新既往史用户频次
     *
     * @param param
     */
    void updateUserSelectCount(Map<String, Object> param);
}
