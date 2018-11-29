package com.alpha.commons.core.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by HP on 2018/6/20.
 */
@Mapper
public interface HisRegisterYyghMapper extends MyMapper<HisRegisterYygh> {
    Integer insertBatch(List<HisRegisterYygh> list);

    /**
     * 查询需要推送的记录
     *
     * @return
     */
    List<HisRegisterYygh> listNeedPushRecord();

    /**
     * 查询患者预约/取号记录的并集
     *
     * @param hisRegisterYygh
     * @return
     */
    List<HisRegisterYygh> listPatientRegisters(HisRegisterYygh hisRegisterYygh);

    /**
     * 查询预约号已来线下取号的记录
     *
     * @param hisRegisterYygh
     * @return
     */
    List<HisRegisterYygh> listAppointmentWhichRegistered(HisRegisterYygh hisRegisterYygh);

    /**
     * 根据门诊号查询患者预约/挂号信息
     *
     * @param hisRegisterYygh
     * @return
     */
    List<HisRegisterYygh> getOneByOutPatient(HisRegisterYygh hisRegisterYygh);

    /**
     * 批量更新状态
     *
     * @param
     */
    void batchUpdateStatus(List<HisRegisterYygh> list);

    List<HisRegisterYygh> getTestAccount(String datenow);

    /**
     * 就诊前一天的预约记录
     * @return
     */
    List<HisRegisterYygh> getAppointmentUserForTomorrow();

    /**
     * 获取就诊当天的预约记录
     * @return
     */
    List<HisRegisterYygh> getAppointmentUserForToday();

    /**
     * 获取就诊当天未取号的预约记录
     * @param param
     * @return
     */
    List<HisRegisterYygh> getAppointmentUserWithoutRegister(HisRegisterYygh param);
}
