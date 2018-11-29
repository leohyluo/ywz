package com.alpha.self.diagnosis.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.self.diagnosis.pojo.BasicWeightInfo;

import java.util.List;
import java.util.Map;

public interface BasicWeightInfoDao extends IBaseDao<BasicWeightInfo, Long> {

    public List<BasicWeightInfo> query(Map<String, Object> param);
}
