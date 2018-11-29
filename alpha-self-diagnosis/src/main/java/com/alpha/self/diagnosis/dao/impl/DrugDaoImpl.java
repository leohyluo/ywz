package com.alpha.self.diagnosis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.self.diagnosis.dao.DrugDao;
import com.alpha.server.rpc.diagnosis.pojo.DrugOnSellDetail;

@Repository
public class DrugDaoImpl extends BaseDao<DrugOnSellDetail, Long> implements DrugDao {
	
	private static final String NAME_SPACE = "com.alpha.server.rpc.diagnosis.pojo.DrugOnSellDetail";

	public DrugDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugOnSellDetail> listByKeyword(String keyword) {
		String statement = NAME_SPACE.concat(".queryByKeyword");
		Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        List<DataRecord> datas = super.selectForList(statement, params);
        List<DrugOnSellDetail> drugList = JavaBeanMap.convertListToJavaBean(datas, DrugOnSellDetail.class);
        return drugList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DrugOnSellDetail getByDrugCode(String drugCode) {
		String statement = NAME_SPACE.concat(".queryByDrugCode");
		Map<String, Object> params = new HashMap<>();
        params.put("drugCode", drugCode);
        List<DataRecord> datas = super.selectForList(statement, params);
        List<DrugOnSellDetail> drugList = JavaBeanMap.convertListToJavaBean(datas, DrugOnSellDetail.class);
        DrugOnSellDetail drug = null;
        if(CollectionUtils.isNotEmpty(drugList)) {
        	drug = drugList.get(0);
        }
        return drug;
	}

	@Override
	public Class<DrugOnSellDetail> getClz() {
		return DrugOnSellDetail.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugOnSellDetail> listUserHotDrug() {
		String statement = NAME_SPACE.concat(".getUserHotDrug");
		Map<String, Object> params = new HashMap<>();
		params.put("threshold", GlobalConstants.HOT_DRUG_THRESHOLD);
		params.put("size", GlobalConstants.HOT_DRUG_COUNT);
        List<DataRecord> datas = super.selectForList(statement, params);
        List<DrugOnSellDetail> drugList = JavaBeanMap.convertListToJavaBean(datas, DrugOnSellDetail.class);
        return drugList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugOnSellDetail> listAnyDrugOrderByDefaultOrder(int size) {
		String statement = NAME_SPACE.concat(".getDefaultHotDrug");
		Map<String, Object> params = new HashMap<>();
		params.put("size", size);
        List<DataRecord> datas = super.selectForList(statement, params);
        List<DrugOnSellDetail> drugList = JavaBeanMap.convertListToJavaBean(datas, DrugOnSellDetail.class);
        return drugList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugOnSellDetail> listByDrugName(String drugName) {
		String statement = NAME_SPACE.concat(".getByDrugName");
		Map<String, Object> params = new HashMap<>();
		params.put("drugName", drugName);
        List<DataRecord> datas = super.selectForList(statement, params);
        List<DrugOnSellDetail> drugList = JavaBeanMap.convertListToJavaBean(datas, DrugOnSellDetail.class);
        return drugList;
	}
}
