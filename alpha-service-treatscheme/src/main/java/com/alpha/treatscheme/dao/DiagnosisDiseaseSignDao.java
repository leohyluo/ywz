package com.alpha.treatscheme.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisDiseaseSign;

import java.util.List;

/**
 * 文章资讯
 * @author Administrator
 *
 */
public interface DiagnosisDiseaseSignDao extends IBaseDao<DiagnosisDiseaseSign, Long> {

	
	/**
	 * 查询疾病体征
	 * @param diseaseCode
	 * @return
	 */
	List<DiagnosisDiseaseSign> listByDiseaseCode(String diseaseCode);
}
