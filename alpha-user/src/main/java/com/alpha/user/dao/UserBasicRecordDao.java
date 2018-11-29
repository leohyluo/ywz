package com.alpha.user.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.user.pojo.vo.MedicalList;

import java.util.List;
import java.util.Map;

public interface UserBasicRecordDao extends IBaseDao<UserBasicRecord, Long> {

    UserBasicRecord findLast(Long userId);
    
    /**
     * 查询用户最近一次完整的问诊记录
     *
     * @param userId
     * @return
     */
    UserBasicRecord findLastCompleted(Long userId);

    UserBasicRecord findByDiagnosisId(Long diagnosisId);

    /**
     * 查询最近一个月回答过的既往史、过敏史
     *
     * @param param 用户id
     * @return 一个月回答过的既往史、过敏史
     */
    List<UserBasicRecord> findByLastMonth(Map<String, Object> param);

    /**
     * 查找主症状模板id
     *
     * @param
     * @return
     */
    String findTemplateId(Long diagnosisId);
    
    /**
     * 根据挂号号码查看是否已完成预问诊
     * @param userId
     * @return
     */
    UserBasicRecord findFinishByUserId(Long userId, String hisRegisterNo);

    /**
     * 根据挂号码/预约码和类型查询已完成的预问诊记录
     * @param pno
     * @param type
     * @return
     */
    UserBasicRecord listFinishByPno(String pno, String type);
    
    /**
     * 根据用户ID查看已完成预问诊的记录
     * @param userId
     * @return
     */
    List<UserBasicRecord> listFinishByUserId(Long userId);
    
    /**
     * 根据用户ID查看当天已完成预问诊的记录
     * @param userId
     * @return
     */
    List<UserBasicRecord> listTodayFinishByUserId(Long userId);
    
    /**
     * 查询当天未确诊的数据
     * @return
     */
    List<UserBasicRecord> listTodayUnConfirm();
    
    /**
     * 获取用户所有就诊记录 
     * @param userId
     * @return
     */
    List<UserBasicRecord> listByUserId(Long userId);
    
    /**
     * 查询用户最后一条就诊记录
     * @param userId
     * @return
     */
    UserBasicRecord getLastFinishByUserId(Long userId);
    
    /**
     * 查找当天未确诊并且已关注微信公众号的数据的数据
     * @return
     */
    List<UserBasicRecord> listWecharUserUnFinishOnToday();
    
    /**
     * 查找具体某一个用户当天未确诊并且已关注微信公众号的数据的数据
     * @param userId
     * @return
     */
    List<UserBasicRecord> listWecharUserUnFinishOnTodayByUserId(Long userId);

    /**
     * 根据医院编码和门诊号获取预问诊就诊记录
     * @param hospitalCode
     * @param hisRegisterNo
     * @return
     */
    UserBasicRecord getByHospitalCodeAndHisRegisterNo(String hospitalCode, String hisRegisterNo);

    /**
     * 取现场号的预问诊记录
     * @param pno
     * @return
     */
    List<UserBasicRecord> getForLive(String pno);

    /**
     * 取预约号的预问诊记录
     * @param pno
     * @return
     */
    List<UserBasicRecord> getForAppointment(String pno);

    UserBasicRecord getByDiagnosisIdAndStatus(Long diagnosisId, String status);

    /**
     * 预约后线下取号查询有没有在预约阶段或取号阶段完成过预问诊
     * @param pno 挂号码
     * @return
     */
    List<UserBasicRecord> listFinishByAppointmentOrOffLine(String pno);

    /**
     * 获取病历列表
     * @return
     */
    List<MedicalList> findByMedicalList(int pageIndex, int pageCount);

    /**
     * 获取病历列表总数
     * @return
     */
    int findByMedicalListCount();
}
