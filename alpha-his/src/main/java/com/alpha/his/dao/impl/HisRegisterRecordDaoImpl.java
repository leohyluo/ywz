package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.his.dao.HisRegisterRecordDao;
import com.alpha.server.rpc.his.pojo.HisRegisterRecord;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HisRegisterRecordDaoImpl extends BaseDao<HisRegisterRecord, Long> implements HisRegisterRecordDao {

	@Override
	public Class<HisRegisterRecord> getClz() {
		return HisRegisterRecord.class;
	}

	public HisRegisterRecordDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	private final String NAME_SPACE = "com.alpha.server.rpc.his.pojo.HisRegisterRecord";


	@Override
	public HisRegisterRecord getByPno(String pno) {
		String statement = NAME_SPACE.concat(".queryByHisRegisterNo");
		Map<String, Object> param = new HashMap<>();
		param.put("pno", pno);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HisRegisterRecord> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HisRegisterRecord.class);
		}
		HisRegisterRecord hisRegisterRecord = CollectionUtils.isNotEmpty(resultList) ? resultList.get(0) : null;
		return hisRegisterRecord;

	}

	@Override
	public HisRegisterRecord getByOutPatientNoAndPno(String outPatientNo, String pno) {
		String statement = NAME_SPACE.concat(".queryByOutPatientNoAndHosNo");
		Map<String, Object> param = new HashMap<>();
		param.put("outPatientNo", outPatientNo);
		param.put("pno", pno);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HisRegisterRecord> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HisRegisterRecord.class);
		}
		HisRegisterRecord hisRegisterRecord = CollectionUtils.isNotEmpty(resultList) ? resultList.get(0) : null;
		return hisRegisterRecord;
	}

	@Override
	public List<HisRegisterRecord> listTodayAppointment(String outPatientNo) {
		String statement = NAME_SPACE.concat(".listAppointment");
		Map<String, Object> param = new HashMap<>();
		param.put("outPatientNo", outPatientNo);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HisRegisterRecord> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HisRegisterRecord.class);
		}
		return resultList;
	}

	@Override
	public List<HisRegisterRecord> listByOutPatientNoAndVisitTime(String outPatientNo, String visitTime) {
		String statement = NAME_SPACE.concat(".listByOutPatientNoAndVisitTime");
		Map<String, Object> param = new HashMap<>();
		param.put("outPatientNo", outPatientNo);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HisRegisterRecord> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HisRegisterRecord.class);
		}
		return resultList;
	}

	@Override
	public Long insertByBatch(List<HisRegisterRecord> t) {
		return Long.valueOf(insert(NAME_SPACE + ".insertByBatch", t));
	}

	@Override
	public List<HisRegisterRecord> listByOutPatientNo(String outPatientNo) {
		String statement = NAME_SPACE.concat(".queryByOutPatientNo");
		Map<String, Object> param = new HashMap<>();
		param.put("outPatientNo", outPatientNo);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HisRegisterRecord> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HisRegisterRecord.class);
		}
		return resultList;
	}

	@Override
	public HisRegisterRecord getByFetchCompleted(String fetchNo) {
		String statement = NAME_SPACE.concat(".getByFetchCompleted");
		Map<String, Object> param = new HashMap<>();
		param.put("fetchNo", fetchNo);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HisRegisterRecord> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HisRegisterRecord.class);
		}
		return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
	}
}
