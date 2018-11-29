package com.alpha.treatscheme.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.treatscheme.dao.DiagnosisDiseaseDeptDao;
import com.alpha.treatscheme.pojo.DiagnosisDiseaseDept;

@Repository
public class DiagnosisDiseaseDeptDaoImpl extends BaseDao<DiagnosisDiseaseDept, Long> implements DiagnosisDiseaseDeptDao {

    public DiagnosisDiseaseDeptDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	private static final String NAMESPACE = "com.alpha.treatscheme.pojo.DiagnosisDiseaseDept";

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisDiseaseDept> listDiagnosisDiseaseDept(String diseaseCode) {
		String statement = NAMESPACE.concat(".queryByDiseaseCode");
		Map<String, Object> params = new HashMap<>();
        params.put("diseaseCode", diseaseCode);
        List<DataRecord> datas = super.selectForList(statement, params);
        List<DiagnosisDiseaseDept> list = JavaBeanMap.convertListToJavaBean(datas, DiagnosisDiseaseDept.class);
        return list;
	}

	@Override
	public Class<DiagnosisDiseaseDept> getClz() {
		return DiagnosisDiseaseDept.class;
	}
}
