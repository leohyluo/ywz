package com.alpha.his.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;

public interface HospitalInfoDao extends IBaseDao<HospitalInfo, Long> {

	
	/**
	 * 根据医院编码查询医院信息
	 * @param hospitalCode
	 * @return
	 */
	HospitalInfo getByHospitalCode(String hospitalCode);
}
