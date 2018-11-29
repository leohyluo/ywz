package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;
import com.alpha.his.dao.HospitalizedNewIllChildDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HospitalizedNewIllChildDaoImpl extends BaseDao<HospitalizedNewIllChild, Long> implements HospitalizedNewIllChildDao {

	private static final String NAME_SPACE = "com.alpha.his.dao.impl.HospitalizedNewIllChildDaoImpl";

	@Override
	public Class<HospitalizedNewIllChild> getClz() {
		return HospitalizedNewIllChild.class;
	}

	public HospitalizedNewIllChildDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}


	@Override
	public HospitalizedNewIllChild getByHospitalCodeAndHosNo(String hospitalCode, String hosNo) {
		String statement = NAME_SPACE.concat(".queryByHospitalCodeAndHosNo");
		Map<String, Object> param = new HashMap<>();
		param.put("hospitalCode", hospitalCode);
		param.put("hosNo", hosNo);
		List<DataRecord> list = super.selectForList(statement, param);
		List<HospitalizedNewIllChild> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, HospitalizedNewIllChild.class);
		}
		HospitalizedNewIllChild hospitalizedNewIllChild = CollectionUtils.isNotEmpty(resultList) ? resultList.get(0) : null;
		return hospitalizedNewIllChild;
	}
}
