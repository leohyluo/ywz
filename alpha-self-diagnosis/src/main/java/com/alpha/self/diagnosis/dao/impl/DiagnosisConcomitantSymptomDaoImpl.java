package com.alpha.self.diagnosis.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.self.diagnosis.dao.DiagnosisConcomitantSymptomDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisConcomitantSymptom;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 */
@Repository
public class DiagnosisConcomitantSymptomDaoImpl extends BaseDao<DiagnosisConcomitantSymptom, Long> implements DiagnosisConcomitantSymptomDao {

    private static final String NAME_SPACE = "com.alpha.server.rpc.diagnosis.pojo.DiagnosisConcomitantSymptom";

    public DiagnosisConcomitantSymptomDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisConcomitantSymptom> getClz() {
        return DiagnosisConcomitantSymptom.class;
    }

    @Override
    public List<DiagnosisConcomitantSymptom> listBySympName(List<String> sympNameList) {
        String statement = NAME_SPACE.concat(".findBySympNames");
        Map<String, Object> params = new HashMap<>();
        params.put("cocnSympNames", sympNameList);
        List<DataRecord> datas = super.selectForList(statement, params);
        List<DiagnosisConcomitantSymptom> concomitantSymptomList = JavaBeanMap.convertListToJavaBean(datas, DiagnosisConcomitantSymptom.class);
        return concomitantSymptomList;
    }
}
