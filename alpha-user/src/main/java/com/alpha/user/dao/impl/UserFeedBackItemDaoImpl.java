package com.alpha.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alpha.commons.enums.AppType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.enums.System;
import com.alpha.user.dao.UserFeedBackItemDao;
import com.alpha.user.pojo.UserFeedBackItem;

@Repository
public class UserFeedBackItemDaoImpl extends BaseDao<UserFeedBackItem, Long> implements UserFeedBackItemDao {

	public UserFeedBackItemDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@Override
	public Class<UserFeedBackItem> getClz() {
		return UserFeedBackItem.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserFeedBackItem> listUserFeedBackItem(AppType appType) {
		String statement = "com.alpha.user.pojo.UserFeedBackItem.queryAll";
		Map<String, Object> param = new HashMap<>();
		param.put("systemType", appType.getValue());
		List<DataRecord> list = super.selectForList(statement, param );
		List<UserFeedBackItem> resultList = new ArrayList<>();
		if (resultList != null) {
			resultList = JavaBeanMap.convertListToJavaBean(list, UserFeedBackItem.class);
		}
		return resultList;
	}

	
}
