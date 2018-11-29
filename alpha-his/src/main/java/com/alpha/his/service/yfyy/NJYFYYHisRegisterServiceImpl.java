package com.alpha.his.service.yfyy;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.core.his.OutPatientService;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.mapper.OpenDepartmentMapper;
import com.alpha.commons.core.pojo.*;
import com.alpha.commons.core.util.DeptUtils;
import com.alpha.commons.core.util.MapToJsonUtil;
import com.alpha.commons.core.util.StaticHttpclientCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2018/9/30.
 */
public class NJYFYYHisRegisterServiceImpl implements OutPatientService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HisRegisterYyghMapper hisRegisterYyghMapper;
    @Autowired
    private OpenDepartmentMapper openDepartmentMapper;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;
    @Value("${Nj_WDSL}")
    private String NJwsdl;



    @Override
    public List<RegisterDTO> registrationInfo(String outPatientNo, String visitTime) {

        try {
            logger.info("获取门诊或者卡号{}，时间{}挂号信息",outPatientNo,visitTime);
            //2.从接口走
            Map map=new HashMap();
            map.put("outPatientNo",outPatientNo);
            visitTime=visitTime+" 00:00:00";
            map.put("visitTime",visitTime);
            String resultJ= StaticHttpclientCall.getGhByMZHM(NJwsdl,"9001", MapToJsonUtil.map2Json(map));
            NJResponse<HisRegisterYygh> response= null;
            response = JSON.parseObject(resultJ,NJResponse.class);
            if(response.getResult().equals("success") && response.getData().size()>0){
                List<HisRegisterYygh> list1= JSON.parseArray(JSON.parseObject(resultJ).getString("data"), HisRegisterYygh.class);
                logger.info("从门诊号接口拿到数据");
                return pare(list1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取挂号信信息异常"+e.toString()
            );
        }
        return null;
    }

    public List<RegisterDTO> pare(List<HisRegisterYygh> list){
        List<RegisterDTO> list1=new ArrayList<>();
        List<HisRegisterYygh> li=new ArrayList<>();
        try {
            list.forEach(e -> {
                if(checkOpenDepartMent(e)){
                    RegisterDTO r=new RegisterDTO();
                    r.setBirthday(e.getBirthday()==null?null:e.getBirthday());
                    r.setDepName(e.getDeptName()==null?null:e.getDeptName());
                    r.setDoctorName(e.getDoctorName()==null?null:e.getDoctorName());
                    r.setOutPatientNo(e.getOutPatientNo()==null?null:e.getOutPatientNo());
                    r.setPno(e.getPno()==null?null:e.getPno());
                    r.setSex(e.getSex().equals("1")?"男":"女");
                    r.setPatientName(e.getPatientName()==null?null:e.getPatientName());
                    r.setVisitTime(e.getVisitTime()==null?null:e.getVisitTime());
                    list1.add(r);
                    e.setSex(e.getSex().equals("1")?"男":"女");
                    e.setType(1);
                    li.add(e);
                }
            });
            if(li.size()>0){
                hisRegisterYyghMapper.insertBatch(li);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("HisRegisterYygh 转换 RegisterDTO 异常"+e.toString());
        }
        return list1;
    }


    public boolean checkOpenDepartMent(HisRegisterYygh hisRegisterYygh){
        List<String> list =DeptUtils.getDepartment();
        for (int i = 0; i < list.size(); i++) {
            if(hisRegisterYygh.getDeptName().contains(list.get(i))){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object patientInfo(String cardNo) {
         return  null;
    }

    @Override
    public List<HisRegisterYygh> hisRegisterYyghInfo(String startTime, String endTime) {
        return null;
    }

    @Override
    public void NoticeData(String startTime, String endTime) {

    }

}
