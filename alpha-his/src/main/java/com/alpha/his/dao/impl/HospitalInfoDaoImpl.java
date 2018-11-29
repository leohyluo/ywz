package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.his.dao.HospitalInfoDao;
import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HospitalInfoDaoImpl extends BaseDao<HospitalInfo, Long> implements HospitalInfoDao {

	@Override
	public Class<HospitalInfo> getClz() {
		return HospitalInfo.class;
	}

	public HospitalInfoDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	private final String NAME_SPACE = "com.alpha.server.rpc.diagnosis.pojo.HospitalInfo";


	@Override
	public HospitalInfo getByHospitalCode(String hospitalCode) {
		String statement = NAME_SPACE.concat(".queryByHospitalCode");
		Map<String, Object> param = new HashMap<>();
		param.put("hospitalCode", hospitalCode);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HospitalInfo> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HospitalInfo.class);
		}
		HospitalInfo hospitalInfo = CollectionUtils.isNotEmpty(resultList) ? resultList.get(0) : null;
		return hospitalInfo;
	}
}
