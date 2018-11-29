package com.alpha.his.service.etyy;

import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;
import com.alpha.server.rpc.his.pojo.HisDiagnosisRecord;

import java.util.List;
import java.util.Map;

/**
 * 医院相关操作逻辑处理类
 * @author Administrator
 *
 */
public interface HospitalService {

	/**
	 * 保存医生工作站编辑后的病历
	 * @param hisDiagnosisRecord
	 */
	void saveHisDiagnosisRecord(HisDiagnosisRecord hisDiagnosisRecord);

    /**
     * 获取医院详细信息
	 * @param hospitalCode
     * @return
     */
	HospitalInfo getHospitalInfo(String hospitalCode);

	/**
	 * 查看医生编辑后的病历
	 * @param diagnosisId
	 * @return
	 */
	HisDiagnosisRecord getByDiagnosisId(Long diagnosisId);

	/**
	 *   获取 患者的挂号信息
	 *  根据 门诊号码 + 就诊时间  格式（2018-03-20）
	 */

	List<RegisterDTO> registrationInfo(String outPatientNo, String visitTime);

	Map<String,Object> HotAdress(String keyword, Integer type);
}
