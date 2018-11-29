package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.his.dao.HisDiagnosisRecordDao;
import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;
import com.alpha.server.rpc.his.pojo.HisDiagnosisRecord;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HisDiagnosisRecordDaoImpl extends BaseDao<HisDiagnosisRecord, Long> implements HisDiagnosisRecordDao {

	private static final String NAME_SPACE = "com.alpha.server.rpc.his.pojo.HisDiagnosisRecord";

	public HisDiagnosisRecordDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@Override
	public Class<HisDiagnosisRecord> getClz() {
		return HisDiagnosisRecord.class;
	}

	@Override
	public HisDiagnosisRecord getByDiagnosisId(Long diagnosisId) {
		String statement = NAME_SPACE.concat(".queryByDiagnosisId");
		Map<String, Object> param = new HashMap<>();
		param.put("diagnosisId", diagnosisId);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HisDiagnosisRecord> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HisDiagnosisRecord.class);
		}
		HisDiagnosisRecord hisDiagnosisRecord = CollectionUtils.isNotEmpty(resultList) ? resultList.get(0) : null;
		return hisDiagnosisRecord;
	}
}
