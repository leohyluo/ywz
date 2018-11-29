package com.alpha.push.mapper;


import com.alpha.push.domain.HisRegisterRecord;

import java.util.List;
import java.util.Map;

public interface HisRegisterRecordMapper {

	/**
	 * 根据挂号码获取挂号信息
	 * @param pno
	 * @return
	 */
	HisRegisterRecord getByPno(String pno);

	void insert(List<HisRegisterRecord> hisRegisterRecordList);

	HisRegisterRecord getByOutPatientNoAndPno(Map<String, String> param);

	void update(HisRegisterRecord hisRegisterRecord);
}
