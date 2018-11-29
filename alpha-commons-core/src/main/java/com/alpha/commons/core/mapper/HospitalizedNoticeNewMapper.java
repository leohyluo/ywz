package com.alpha.commons.core.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.commons.core.pojo.HospitalizedNoticeNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by HP on 2018/3/29.
 */
public interface HospitalizedNoticeNewMapper extends MyMapper<HospitalizedNoticeNew>{

    Integer saveDo(HospitalizedNoticeNew hospitalizedNoticeNew);
    HospitalizedNoticeNew selectDo(@Param("outPatientNo")String outPatientNo);

    List<HospitalizedNoticeNew> selectAllNotice();

    Integer insertBatch(List<HospitalizedNoticeNew> list1);
}
