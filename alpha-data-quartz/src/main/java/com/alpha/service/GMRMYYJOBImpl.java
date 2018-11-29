package com.alpha.service;

import com.alpha.commons.core.his.OutPatientService;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.commons.core.util.DeptUtils;
import com.alpha.commons.core.util.DruidManager;
import com.alpha.fegin.SchedualServiceDataAdvance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by edz on 2018/10/18.
 * 光明医院 定时任务
 */
@Service
public class GMRMYYJOBImpl implements OutPatientService {
    @Resource
    private HisRegisterYyghMapper hisRegisterYyghMapper;
    @Resource
    private SchedualServiceDataAdvance schedualServiceDataAdvance;

    private static Logger logger = LoggerFactory.getLogger(GMRMYYJOBImpl.class);

    @Override
    public List<RegisterDTO> registrationInfo(String outPatientNo, String visitTime) {
        return null;
    }

    @Override
    public Object patientInfo(String cardNo) {
        return null;
    }

    @Override
    public List<HisRegisterYygh> hisRegisterYyghInfo(String startTime, String endTime) {
       logger.info("光明人民医院");
        List<HisRegisterYygh> result=new ArrayList<>();
        List<Map<String, Object>> list= DruidManager.yygh(startTime,endTime,DruidManager.all);
        List<String> dept= DeptUtils.getDepartment();
        if(null !=list && list.size()>0){
            list.stream().forEach(e -> {
                dept.stream().forEach(de -> {
                    if(((String)e.get("DEPTNAME")).contains(de)) {
                        HisRegisterYygh registerDTO = new HisRegisterYygh();
                        registerDTO.setBirthday(e.get("BIRTHDAY") == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp) e.get("BIRTHDAY")));
                        registerDTO.setDeptName(e.get("DEPTNAME") == null ? null : (String) e.get("DEPTNAME"));
                        registerDTO.setDoctorName(e.get("DOCTORNAME") == null ? null : (String) e.get("DOCTORNAME"));
                        registerDTO.setOutPatientNo(e.get("OUTPATIENTNO") == null ? null : (String) e.get("OUTPATIENTNO"));
                        registerDTO.setPatientCardNo(e.get("PATIENTCARDNO") == null ? null : (String) e.get("PATIENTCARDNO"));
                        registerDTO.setPatientName(e.get("PATIENTNAME") == null ? null : (String) e.get("PATIENTNAME"));
                        registerDTO.setPno(e.get("PNO") == null ? null : (String.valueOf(e.get("PNO"))));
                        registerDTO.setSex(e.get("SEX") == null ? null : (String) e.get("SEX"));
                        registerDTO.setVisitTime(e.get("VISITTIME") == null ? null : getDate(e.get("VISITTIME").toString()));
                        registerDTO.setCreateTime(e.get("CREATETIME") == null ? null : getDate(e.get("CREATETIME").toString()));
                        registerDTO.setPhoneNew(e.get("PHONENEW") == null ? null : (String) e.get("PHONENEW"));
                        registerDTO.setPhone(e.get("PHONE") == null ? null : (String) e.get("PHONE"));
                        registerDTO.setIntervalTime(e.get("INTERVALTIME") == null ? null : (String) e.get("INTERVALTIME"));
                        registerDTO.setType(e.get("TYPE") == null ? null : (Integer.parseInt((String) e.get("TYPE"))));
                        registerDTO.setYno(e.get("PNONEW") == null ? null : (String.valueOf(e.get("PNONEW"))));
                        registerDTO.setStatus(0);
                        registerDTO.setCardNo(e.get("CARDNO") == null ? null : (String) e.get("CARDNO"));
                        result.add(registerDTO);
                    }
                });
            });
            push();
        }
        return result;
    }

    public static String getDate(String arg) {
        try {
            Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(arg);
            String data=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
           return data;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void NoticeData(String startTime, String endTime) {
         logger.info("光明人民医院没有住院业务");
    }

    public void push(){
        List<HisRegisterYygh> registerList = hisRegisterYyghMapper.listNeedPushRecord();
        if(null !=registerList && registerList.size()>0){
            registerList.stream().forEach(e -> {
                schedualServiceDataAdvance.invokeDataAdvanceService(e);
            });
        }
    }
}
