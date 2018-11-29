package com.alpha.his.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.his.pojo.HisDiagnosisRecord;

/**
 * 医院诊断结果
 * @author Administrator
 *
 */
public interface HisDiagnosisRecordDao extends IBaseDao<HisDiagnosisRecord, Long> {

    /**
     * 根据就诊编号获取医生诊断记录
     * @param diagnosisId
     * @return
     */
    HisDiagnosisRecord getByDiagnosisId(Long diagnosisId);

}
