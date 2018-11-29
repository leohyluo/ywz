package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.his.dao.SysNationDao;
import com.alpha.his.dao.SysNationalityDao;
import com.alpha.server.rpc.his.pojo.SysConstant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SysNationalityDaoImpl extends BaseDao<SysConstant, Long> implements SysNationalityDao {
    @Override
    public Class<SysConstant> getClz() {
        return SysConstant.class;
    }
    public SysNationalityDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }
}
