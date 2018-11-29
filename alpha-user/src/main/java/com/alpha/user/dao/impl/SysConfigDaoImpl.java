package com.alpha.user.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.user.dao.SysConfigDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysConfigDaoImpl extends BaseDao<SysConfig, Long> implements SysConfigDao {

	public SysConfigDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysConfig querySysConfig(String key) {
		String statement = "com.alpha.server.rpc.sys.SysConfig.queryByKey";
		Map<String, Object> param = new HashMap<>();
		param.put("configKey", key);
		List<DataRecord> list = super.selectForList(statement, param);
		List<SysConfig> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, SysConfig.class);
		}
		SysConfig sysConfig = CollectionUtils.isNotEmpty(resultList) ? resultList.get(0) : null;
		return sysConfig;
	}

	@Override
	public List<SysConfig> findAll() {
		String statement = "com.alpha.server.rpc.sys.SysConfig.queryAll";
		List<DataRecord> list = super.selectForList(statement, new HashMap<>());
		List<SysConfig> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, SysConfig.class);
		}
		return resultList;
	}

	@Override
	public Class<SysConfig> getClz() {
		// TODO Auto-generated method stub
		return SysConfig.class;
	}
}
