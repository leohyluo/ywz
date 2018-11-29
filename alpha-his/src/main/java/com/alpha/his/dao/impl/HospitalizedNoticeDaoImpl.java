package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.server.rpc.his.pojo.HospitalizedNotice;
import com.alpha.his.dao.HospitalizedNoticeDao;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HospitalizedNoticeDaoImpl extends BaseDao<HospitalizedNotice, Long> implements HospitalizedNoticeDao {

	private static final String NAME_SPACE = "com.alpha.his.dao.impl.HospitalizedNoticeDaoImpl";

	@Override
	public Class<HospitalizedNotice> getClz() {
		return HospitalizedNotice.class;
	}

	public HospitalizedNoticeDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}


	@Override
	public HospitalizedNotice getByHospitalCodeAndHosNo(String hospitalCode, String hosNo) {
		String statement = NAME_SPACE.concat(".queryByHospitalCodeAndHosNo");
		Map<String, Object> param = new HashMap<>();
		param.put("hospitalCode", hospitalCode);
		param.put("hosNo", hosNo);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HospitalizedNotice> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HospitalizedNotice.class);
		}
		HospitalizedNotice hospitalizedNotice = CollectionUtils.isNotEmpty(resultList) ? resultList.get(0) : null;
		return hospitalizedNotice;
	}

	@Override
	public List<HospitalizedNotice> listByHospitalCodeAndOutPatientNo(String hospitalCode, String outPatientNo) {
		String statement = NAME_SPACE.concat(".queryByHospitalCodeAndOutPatientNo");
		Map<String, Object> param = new HashMap<>();
		param.put("hospitalCode", hospitalCode);
		param.put("outPatientNo", outPatientNo);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HospitalizedNotice> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HospitalizedPatientInfo.class);
		}
		return resultList;
	}
}
