package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;
import com.alpha.his.dao.HospitalizedCommonIllChildDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HospitalizedCommonIllChildDaoImpl extends BaseDao<HospitalizedCommonIllChild, Long> implements HospitalizedCommonIllChildDao {

	private static final String NAME_SPACE = "com.alpha.his.dao.impl.HospitalizedCommonIllChildDaoImpl";

	@Override
	public Class<HospitalizedCommonIllChild> getClz() {
		return HospitalizedCommonIllChild.class;
	}

	public HospitalizedCommonIllChildDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@Override
	public HospitalizedCommonIllChild getByHospitalCodeAndHosNo(String hospitalCode, String hosNo) {
		String statement = NAME_SPACE.concat(".queryByHospitalCodeAndHosNo");
		Map<String, Object> param = new HashMap<>();
		param.put("hospitalCode", hospitalCode);
		param.put("hosNo", hosNo);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HospitalizedCommonIllChild> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HospitalizedCommonIllChild.class);
		}
		HospitalizedCommonIllChild hospitalizedCommonIllChild = CollectionUtils.isNotEmpty(resultList) ? resultList.get(0) : null;
		return hospitalizedCommonIllChild;
	}
}
