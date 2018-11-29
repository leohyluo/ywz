package com.alpha.user.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.server.rpc.user.pojo.UserMember;
import com.alpha.user.dao.UserMemberDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserMemberDaoImpl extends BaseDao<UserMember, Long> implements UserMemberDao {

    private static final String NAME_SPACE = "com.alpha.user.mapper.UserMemberMapper";

    @SuppressWarnings("unchecked")
    @Override
    public List<UserMember> find(Map<String, Object> map) {
        String statement = NAME_SPACE + ".find";
        List<DataRecord> list = super.selectForList(statement, map);
        List<UserMember> resultList = new ArrayList<>();
        if (resultList != null) {
            resultList = JavaBeanMap.convertListToJavaBean(list, UserMember.class);
        }
        return resultList;
    }

    public UserMemberDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<UserMember> getClz() {
        return UserMember.class;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMember> listByUserId(Long userId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		String statement = NAME_SPACE + ".find";
		
        List<DataRecord> list = super.selectForList(statement, param);
        List<UserMember> resultList = new ArrayList<>();
        if (resultList != null) {
            resultList = JavaBeanMap.convertListToJavaBean(list, UserMember.class);
        }
        return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMember> listByUserIdAndMemberName(Long userId, String memberName) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("memberName", memberName);
		String statement = NAME_SPACE + ".find";
		
        List<DataRecord> list = super.selectForList(statement, param);
        List<UserMember> resultList = new ArrayList<>();
        if (resultList != null) {
            resultList = JavaBeanMap.convertListToJavaBean(list, UserMember.class);
        }
        return resultList;
	}


}
