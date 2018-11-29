package com.alpha.his.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.his.pojo.HisRegisterRecord;

import java.util.List;

public interface HisRegisterRecordDao extends IBaseDao<HisRegisterRecord, Long> {

	/**
	 * 根据挂号码获取挂号信息
	 * @param pno
	 * @return
	 */
	HisRegisterRecord getByPno(String pno);

	/**
	 * 根据门诊号、挂号码获取挂号信息
	 * @param outPatientNo
	 * @param pno
	 * @return
	 */
	HisRegisterRecord getByOutPatientNoAndPno(String outPatientNo, String pno);

	/**
	 * 查询今天和以后的没被取号的预约记录
	 * @param outPatientNo
	 * @return
	 */
	List<HisRegisterRecord> listTodayAppointment(String outPatientNo);

	/**
	 * 根据门诊号、挂号码从本地获取挂号信息
	 * @param outPatientNo
	 * @param visitTime
	 * @return
	 */
	List<HisRegisterRecord> listByOutPatientNoAndVisitTime(String outPatientNo, String visitTime);

	Long insertByBatch(List<HisRegisterRecord> t);

	List<HisRegisterRecord> listByOutPatientNo(String outPatientNo);

	HisRegisterRecord getByFetchCompleted(String fetchNo);
}
