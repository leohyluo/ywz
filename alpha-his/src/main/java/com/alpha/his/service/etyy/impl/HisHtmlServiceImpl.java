package com.alpha.his.service.etyy.impl;

import com.alpha.his.pojo.dto.HisHtmlDTO;
import com.alpha.his.service.etyy.HisHtmlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2018/5/29.
 */
@Service
public class HisHtmlServiceImpl implements HisHtmlService {
    /**
     * 根据医生名字查询 当天所有就诊病历
     * @param doctorName
     * @param visitTime
     * @return
     */
    @Override
    public List<HisHtmlDTO> getHtml(String doctorName, String visitTime) {
        List<HisHtmlDTO> list=new ArrayList<>();

//        1.去获取病历

//        2.根据patientId获取门诊信息

//        3.根据门诊信息获取挂号信息


        return list;
    }
}
