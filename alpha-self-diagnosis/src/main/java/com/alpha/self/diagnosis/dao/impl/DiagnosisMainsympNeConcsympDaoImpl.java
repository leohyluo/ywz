package com.alpha.self.diagnosis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympNeConcsympDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympNeConcsymp;


@Repository
public class DiagnosisMainsympNeConcsympDaoImpl extends BaseDao<DiagnosisMainsympNeConcsymp, Long> implements DiagnosisMainsympNeConcsympDao {

	public DiagnosisMainsympNeConcsympDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisMainsympNeConcsymp> listDiagnosisMainsympNeConcsymp(String mainSympCode, String systemCode) {
		Map<String, Object> param = new HashMap<>();
		param.put("mainSympCode", mainSympCode);
		param.put("systemCode", systemCode);
		List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympNeConcsymp.list", param);
		List<DiagnosisMainsympNeConcsymp> result = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympNeConcsymp.class);
		return result;
	}

	@Override
	public List<DiagnosisMainsympNeConcsymp> listByMainSympCode(String mainSympCode) {
		Map<String, Object> param = new HashMap<>();
		param.put("mainSympCode", mainSympCode);
		List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympNeConcsymp.listByMainSympCode", param);
		List<DiagnosisMainsympNeConcsymp> result = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympNeConcsymp.class);
		return result;
	}

	@Override
	public List<DiagnosisMainsympNeConcsymp> listByHospitalCodeAndMainSympCode(String hospitalCode, String mainSympCode) {
		Map<String, Object> param = new HashMap<>();
		param.put("mainSympCode", mainSympCode);
		param.put("hospitalCode", hospitalCode);
		List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympNeConcsymp.listByHospitalCodeAndMainSympCode", param);
		List<DiagnosisMainsympNeConcsymp> result = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympNeConcsymp.class);
		return result;
	}

	@Override
	public Class<DiagnosisMainsympNeConcsymp> getClz() {
		return DiagnosisMainsympNeConcsymp.class;
	}


   

}
