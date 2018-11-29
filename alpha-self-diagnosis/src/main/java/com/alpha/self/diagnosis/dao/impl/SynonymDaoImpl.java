package com.alpha.self.diagnosis.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.self.diagnosis.dao.SynonymDao;
import com.alpha.self.diagnosis.pojo.Synonym;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SynonymDaoImpl extends BaseDao<Synonym, Long> implements SynonymDao {


    private static final String NAME_SPACE = "com.alpha.self.diagnosis.pojo.SynonymMapper";


    @Override
    public List<Synonym> query(Map<String, Object> param) {
        String statement = NAME_SPACE + ".query";
        List<Synonym> synonymList = super.selectList(statement, param);
        return synonymList;
    }

    public SynonymDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<Synonym> getClz() {
        return Synonym.class;
    }
}
