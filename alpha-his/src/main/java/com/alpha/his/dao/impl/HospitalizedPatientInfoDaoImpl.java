package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfo;
import com.alpha.his.dao.HospitalizedPatientInfoDao;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfoNew;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HospitalizedPatientInfoDaoImpl extends BaseDao<HospitalizedPatientInfoNew, Long> implements HospitalizedPatientInfoDao {

	private static final String NAME_SPACE = "com.alpha.his.dao.impl.HospitalizedPatientInfoDaoImpl";
	@Override
	public Class<HospitalizedPatientInfoNew> getClz() {
		return HospitalizedPatientInfoNew.class;
	}

	public HospitalizedPatientInfoDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@Override
	public HospitalizedPatientInfo getByHospitalCodeAndHosNo(String hospitalCode, String hosNo) {
		String statement = NAME_SPACE.concat(".queryByHospitalCodeAndHosNo");
		Map<String, Object> param = new HashMap<>();
		param.put("hospitalCode", hospitalCode);
		param.put("hosNo", hosNo);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HospitalizedPatientInfo> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HospitalizedPatientInfo.class);
		}
		HospitalizedPatientInfo hospitalizedPatientInfo = CollectionUtils.isNotEmpty(resultList) ? resultList.get(0) : null;
		return hospitalizedPatientInfo;
	}

}
