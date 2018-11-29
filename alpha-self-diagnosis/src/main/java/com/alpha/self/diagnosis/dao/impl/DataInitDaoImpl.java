package com.alpha.self.diagnosis.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.pojo.DiagnosisDisease;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.self.diagnosis.dao.DataInitDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by MR.Wu on 2018-04-09.
 */
@Repository
public class DataInitDaoImpl extends BaseDao<DiagnosisDisease, Long>  implements DataInitDao{

    public DataInitDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisDisease> getClz() {
        return null;
    }

    @Override
    public List<DiagnosisDisease> getAllDiagnosisDisease() {
        List<DataRecord> dataRecord = super.selectForList("com.alpha.commons.core.pojo.DiagnosisDisease.getAllDiagnosisDisease", null);
        List<DiagnosisDisease> diagnosisDiseaseList = JavaBeanMap.convertListToJavaBean(dataRecord, DiagnosisDisease.class);
        return diagnosisDiseaseList;
    }
}
