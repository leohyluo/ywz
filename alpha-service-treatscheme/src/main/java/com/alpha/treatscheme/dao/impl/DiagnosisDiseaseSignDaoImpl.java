package com.alpha.treatscheme.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.treatscheme.dao.DiagnosisDiseaseSignDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisDiseaseSign;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DiagnosisDiseaseSignDaoImpl extends BaseDao<DiagnosisDiseaseSign, Long> implements DiagnosisDiseaseSignDao {

	private final String NAME_SPACE = "com.alpha.server.rpc.diagnosis.pojo.DiagnosisDiseaseSign";

	@Override
	public Class<DiagnosisDiseaseSign> getClz() {
		return DiagnosisDiseaseSign.class;
	}

	public DiagnosisDiseaseSignDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@Override
	public List<DiagnosisDiseaseSign> listByDiseaseCode(String diseaseCode) {
		String statement = NAME_SPACE.concat(".queryByDiseaseCode");
		Map<String, Object> params = new HashMap<>();
		params.put("diseaseCode", diseaseCode);
		List<DataRecord> datas = super.selectForList(statement, params);
		List<DiagnosisDiseaseSign> list = JavaBeanMap.convertListToJavaBean(datas, DiagnosisDiseaseSign.class);
		return list;
	}
}
