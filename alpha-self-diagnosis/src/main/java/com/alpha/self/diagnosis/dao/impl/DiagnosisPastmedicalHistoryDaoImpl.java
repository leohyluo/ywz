package com.alpha.self.diagnosis.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.self.diagnosis.dao.DiagnosisPastmedicalHistoryDao;
import com.alpha.self.diagnosis.pojo.vo.DiseaseVo;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisPastmedicalHistory;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisSubpastmedicalHistory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DiagnosisPastmedicalHistoryDaoImpl extends BaseDao<DiagnosisPastmedicalHistory, Long>
        implements DiagnosisPastmedicalHistoryDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<DiagnosisPastmedicalHistory> queryPastmedicalHistory(Map<String, Object> param) {
        List<DataRecord> list = super.selectForList("com.alpha.commons.mapper.DiagnosisPastmedicalHistoryMapper.queryPastmedicalHistory", param);
        List<DiagnosisPastmedicalHistory> result = new ArrayList<>();
        if (list != null) {
            result = JavaBeanMap.convertListToJavaBean(list, DiagnosisPastmedicalHistory.class);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DiagnosisSubpastmedicalHistory> querySubPastmedicalHistory(Map<String, Object> param) {
        String statement = "com.alpha.self.diagnosis.mapper.DiagnosisPastmedicalHistoryMapper.querySubPastmedicalHistory";
        List<DataRecord> list = super.selectForList(statement, param);
        List<DiagnosisSubpastmedicalHistory> result = new ArrayList<>();
        if (list != null) {
            result = JavaBeanMap.convertListToJavaBean(list, DiagnosisSubpastmedicalHistory.class);
        }
        return result;
    }

    @Override
    public List<DiseaseVo> querySelectedPastmedicalHistory(Map<String, Object> param) {
        String statement = "com.alpha.commons.mapper.DiagnosisPastmedicalHistoryMapper.querySelectedPastmedicalHistory";
        List<DiseaseVo> diseasevoList = new ArrayList<>();
        List<DataRecord> list = super.selectList(statement, param);
        list.forEach(e -> {
            DiseaseVo vo = new DiseaseVo();
            vo.setDiseaseCode(e.getString("disease_code"));
            vo.setDiseaseName(e.getString("disease_name"));
            vo.setDescription(e.getString("description"));
            diseasevoList.add(vo);
        });
        return diseasevoList;
    }


    @Override
    public void updateUserSelectCount(Map<String, Object> param) {
        String statement = "com.alpha.commons.mapper.DiagnosisPastmedicalHistoryMapper.updateUserSelectCount";
        super.updateByStatement(statement, param);
    }

    @Override
    public List<DiagnosisPastmedicalHistory> searchPastmedicalHistory(Map<String, Object> param) {
        List<DataRecord> list = super.selectForList("com.alpha.commons.mapper.DiagnosisPastmedicalHistoryMapper.searchPastmedicalHistory", param);
        List<DiagnosisPastmedicalHistory> result = new ArrayList<>();
        if (list != null) {
            result = JavaBeanMap.convertListToJavaBean(list, DiagnosisPastmedicalHistory.class);
        }

        return result;
    }

    @Override
    public List<DiagnosisPastmedicalHistory> listByMainSympCodeAndSystemCode(String mainSympCode, String systemCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("mainSympCode", mainSympCode);
        param.put("systemCode", systemCode);

        List<DataRecord> list = super.selectForList("com.alpha.commons.mapper.DiagnosisPastmedicalHistoryMapper.queryByMainSympCodeAndSystemCode", param);
        List<DiagnosisPastmedicalHistory> result = new ArrayList<>();
        if (list != null) {
            result = JavaBeanMap.convertListToJavaBean(list, DiagnosisPastmedicalHistory.class);
        }

        return result;
    }

    @Autowired
    public DiagnosisPastmedicalHistoryDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisPastmedicalHistory> getClz() {
        return DiagnosisPastmedicalHistory.class;
    }

}
