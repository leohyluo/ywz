package com.alpha.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.server.rpc.user.pojo.UserFeedback;
import com.alpha.user.dao.UserFeedbackDao;

@Repository
public class UserFeedbackDaoImpl extends BaseDao<UserFeedback, Long> implements UserFeedbackDao {

    private static final String NAME_SPACE = "com.alpha.server.rpc.user.pojo.UserFeedback";
    

    public UserFeedbackDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<UserFeedback> getClz() {
        return UserFeedback.class;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<UserFeedback> listUserFeedback(Long diagnosisId) {
		String statement = NAME_SPACE.concat(".getByDiagnosisId");
		Map<String, Object> param = new HashMap<>();
		param.put("diagnosisId", diagnosisId);
		List<DataRecord> list = super.selectForList(statement, param);
        List<UserFeedback> resultList = new ArrayList<>();
        if (resultList != null) {
            resultList = JavaBeanMap.convertListToJavaBean(list, UserFeedback.class);
        }
        return resultList;
	}



}
