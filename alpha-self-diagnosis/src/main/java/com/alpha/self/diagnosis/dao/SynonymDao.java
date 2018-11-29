package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.self.diagnosis.pojo.Synonym;

import java.util.List;
import java.util.Map;

public interface SynonymDao extends IBaseDao<Synonym, Long> {

    List<Synonym> query(Map<String, Object> param);
}
