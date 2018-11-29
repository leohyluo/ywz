package com.alpha.his.service.etyy.impl;


import com.alpha.commons.util.CollectionUtils;
import com.alpha.his.dao.HisRegisterRecordDao;
import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.his.service.HisServiceFactory.OutPatientServiceFactory;
import com.alpha.his.service.etyy.HospitalService;
import com.alpha.his.service.etyy.RegisterService;
import com.alpha.server.rpc.his.pojo.HisRegisterRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RegisterServiceImpl implements RegisterService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private HisRegisterRecordDao hisRegisterRecordDao;
    @Resource
    HospitalService hospitalService;

    @Value("${hospital.code}")
    private  String code;

    @Override
    public List<RegisterDTO> getUserRegisterInfo(String outPatientNo, String visitTime) {
        if(StringUtils.isBlank(outPatientNo) || StringUtils.isBlank(visitTime)){
              return null;
        }
        List<RegisterDTO> registerList = null;
        try {
            //不同医院 根据门诊号+时间获取 挂号信息不一样
            registerList=OutPatientServiceFactory.createService(code).registrationInfo(outPatientNo,visitTime);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取挂号信息异常："+e.toString());
            return null;
        }
        return registerList;

    }

    @Override
    public HisRegisterRecord getRegisterRecord(String outPatientNo, String pno) {
        return hisRegisterRecordDao.getByOutPatientNoAndPno(outPatientNo, pno);
    }

    @Override
    public void updateHisRegisterRecord(HisRegisterRecord hisRegisterRecord) {
        hisRegisterRecordDao.update(hisRegisterRecord);
    }

    @Override
    public List<HisRegisterRecord> registerDTO2HisRegisterRecord(List<RegisterDTO> registerDTOList) {
        List<HisRegisterRecord> hisRegisterRecordList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(registerDTOList)) {
            for(RegisterDTO item : registerDTOList) {
                HisRegisterRecord hisRegisterRecord = new HisRegisterRecord();
                hisRegisterRecord.setOutPatientNo(item.getOutPatientNo());
                hisRegisterRecord.setPatientName(item.getPatientName());
                hisRegisterRecord.setPno(item.getPno());
                hisRegisterRecord.setSex(item.getSex());
                hisRegisterRecord.setBirthday(item.getBirthday());
                hisRegisterRecord.setIdCard(item.getPatientCardNo());
                hisRegisterRecord.setDeptName(item.getDepName());
                hisRegisterRecord.setDoctorName(item.getDoctorName());
                hisRegisterRecord.setVisitTime(item.getVisitTime());
                hisRegisterRecord.setCreateTime(new Date());
                hisRegisterRecord.setUpdateTime(new Date());
                hisRegisterRecordList.add(hisRegisterRecord);
            }
        }
        return  hisRegisterRecordList;
    }

    @Override
    public void saveHisRegisterRecord(List<HisRegisterRecord> hisRegisterRecordList) {
        hisRegisterRecordDao.insert(hisRegisterRecordList);
    }

    @Override
    public List<HisRegisterRecord> listByOutPatientNoAndVisitTime(String outPatientNo, String visitTime) {
        return hisRegisterRecordDao.listByOutPatientNoAndVisitTime(outPatientNo, visitTime);
    }

    @Override
    public List<HisRegisterRecord> listByOutPatientNo(String outPatientNo) {
        return hisRegisterRecordDao.listByOutPatientNo(outPatientNo);
    }

    @Override
    public HisRegisterRecord getHisRegisterRecord(String hisRegisterNo) {
        return hisRegisterRecordDao.getByPno(hisRegisterNo);
    }
    @Override
    public Long saveByBatch(List<HisRegisterRecord> hisRegisterRecordList){
        return  hisRegisterRecordDao.insertByBatch(hisRegisterRecordList);
    }
}
