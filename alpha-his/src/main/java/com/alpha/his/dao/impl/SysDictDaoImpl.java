package com.alpha.his.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.his.dao.SysDictDao;
import com.alpha.his.dao.SysNationDao;
import com.alpha.server.rpc.his.pojo.SysConstant;
import com.alpha.server.rpc.his.pojo.SysDict;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SysDictDaoImpl extends BaseDao<SysDict, Long> implements SysDictDao {
    @Override
    public Class<SysDict> getClz() {
        return SysDict.class;
    }
    public SysDictDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }
}
