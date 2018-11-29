package com.alpha.self.diagnosis.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainSymptomsDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 */
@Repository
public class DiagnosisMainSymptomsDaoImpl extends BaseDao<DiagnosisMainSymptoms, Long> implements DiagnosisMainSymptomsDao {


    @SuppressWarnings("unchecked")
    @Override
    public List<DiagnosisMainSymptoms> query(Map<String, Object> param) {
        String statement = "com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms.queryByKeyword";
        List<DiagnosisMainSymptoms> result = new ArrayList<>();
        result = super.selectForListObject(statement, param);
//        if (list != null) {
//            result = JavaBeanMap.convertListToJavaBean(list, DiagnosisMainSymptoms.class);
//        }
        return result;
    }

    @Override
    public List<DiagnosisMainSymptoms> listByObjectVersion(Integer objectVersion) {
        String statement = "com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms.queryByObjectVersion";
        List<DiagnosisMainSymptoms> result = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put("objectVersion", objectVersion);
        result = super.selectForListObject(statement, param);
        return result;
    }

    @Override
    public DiagnosisMainSymptoms queryByName(String mainSymptomName) {
        String statement = "com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms.queryBySymptomName";
        List<DiagnosisMainSymptoms> result = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put("sympName", mainSymptomName);
        result = super.selectForListObject(statement, param);
        if(CollectionUtils.isNotEmpty(result))
            return result.get(0);
        return null;
    }

    @Override
    public List<DiagnosisMainSymptoms> listBySympCodeList(List<String> sympCodeList) {
        String statement = "com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms.queryBySympCodeList";
        Map<String, Object> param = new HashMap<>();
        param.put("sympCodeList", sympCodeList);
        List<DiagnosisMainSymptoms> result = super.selectForListObject(statement, param);
        return result;
    }

    @Override
    public List<DiagnosisMainSymptoms> listMainSymptomOfExtQuestion(String diagnosisId) {
        String statement = "com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms.queryMainSymptomOfExtQuestion";
        Map<String, Object> param = new HashMap<>();
        param.put("diagnosisId", diagnosisId);
        List<DiagnosisMainSymptoms> result = super.selectForListObject(statement, param);
        return result;
    }

    @Override
    public List<DiagnosisMainSymptoms> listConstructSympOfMainSymptom(String mainSympCode, String systemCode) {
        String statement = "com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms.queryConstructSympOfMainSymptom";
        Map<String, Object> param = new HashMap<>();
        param.put("mainSympCode", mainSympCode);
        param.put("systemCode", systemCode);
        List<DiagnosisMainSymptoms> result = super.selectForListObject(statement, param);
        return result;
    }

    @Autowired
    public DiagnosisMainSymptomsDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisMainSymptoms> getClz() {
        return DiagnosisMainSymptoms.class;
    }


}
