package com.alpha.commons.core.mapper;


import com.alpha.commons.MyMapper;
import com.alpha.commons.core.pojo.HospitalizedAo;
import com.alpha.commons.core.pojo.HospitalizedPatientInfoNew1;
import org.apache.ibatis.annotations.Param;


/**
 * Created by HP on 2018/3/27.
 */
public interface HospitalizedPatientInfoNew1Mapper extends MyMapper<HospitalizedPatientInfoNew1>{
    Integer insertHospitalizedPatientInfoNew1(HospitalizedPatientInfoNew1 hospitalizedPatientInfoNew1);
    HospitalizedAo getPatientHospitalizedHis(@Param("outPatientNo") String outPatientNo);
    HospitalizedPatientInfoNew1 selectMaxVisit(@Param("outPatientNo") String outPatientNo);
    HospitalizedPatientInfoNew1 hisPatientInfo(@Param("outPatientNo")String outPatientNo,@Param("patientName")String patientName);

    HospitalizedPatientInfoNew1 getByNoticeIdAndStatus(@Param("noticeId") String noticeId, @Param("status") Integer status);
}
