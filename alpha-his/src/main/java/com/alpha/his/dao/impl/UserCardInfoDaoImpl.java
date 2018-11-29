package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.his.dao.UserCardInfoDao;
import com.alpha.server.rpc.his.pojo.UserCard;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserCardInfoDaoImpl extends BaseDao<UserCard, Long> implements UserCardInfoDao {
    @Override
    public Class<UserCard> getClz() {
        return UserCard.class;
    }

    public UserCardInfoDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }
}
