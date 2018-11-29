package com.alpha.self.diagnosis.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.self.diagnosis.dao.BasicWeightInfoDao;
import com.alpha.self.diagnosis.pojo.BasicWeightInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BasicWeightInfoDaoImpl extends BaseDao<BasicWeightInfo, Long> implements BasicWeightInfoDao {

    private static final String NAME_SPACE = "com.alpha.self.diagnosis.mapper.BasicWeightInfoMapper";

    @Override
    @SuppressWarnings("unchecked")
    public List<BasicWeightInfo> query(Map<String, Object> param) {
        String statement = NAME_SPACE + ".query";
        List<DataRecord> list = super.selectForList(statement, param);
        List<BasicWeightInfo> result = new ArrayList<>();
        if (list != null) {
            result = JavaBeanMap.convertListToJavaBean(list, BasicWeightInfo.class);
        }
        return result;
    }

    public BasicWeightInfoDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<BasicWeightInfo> getClz() {
        return BasicWeightInfo.class;
    }

}
