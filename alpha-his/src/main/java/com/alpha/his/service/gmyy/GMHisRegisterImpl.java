package com.alpha.his.service.gmyy;

import com.alpha.commons.core.his.OutPatientService;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.commons.core.util.DeptUtils;
import com.alpha.commons.core.util.DruidManager;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2018/9/30.
 * 光明人民医院
 */
public class GMHisRegisterImpl implements OutPatientService{

    @Override
    public List<RegisterDTO> registrationInfo(String outPatientNo, String visitTime) {
        List<Map<String, Object>> list= DruidManager.yygh(visitTime,outPatientNo,DruidManager.alive);
        List<RegisterDTO> result=new ArrayList<>();
        List<String> dept=DeptUtils.getDepartment();
        if(null != list && list.size()>0){
            RegisterDTO registerDTO=new RegisterDTO();
            list.stream().forEach(e -> {
                dept.stream().forEach(de -> {
                    if(((String)e.get("DEPTNAME")).contains(de)){
                        registerDTO.setBirthday(e.get("BIRTHDAY")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)e.get("BIRTHDAY")));
                        registerDTO.setDepName(e.get("DEPTNAME")==null?null:(String)e.get("DEPTNAME"));
                        registerDTO.setDoctorName(e.get("DOCTORNAME")==null?null:(String)e.get("DOCTORNAME"));
                        registerDTO.setOutPatientNo(e.get("OUTPATIENTNO")==null?null:(String)e.get("OUTPATIENTNO"));
                        registerDTO.setPatientCardNo(e.get("PATIENTCARDNO")==null?null:(String)e.get("PATIENTCARDNO"));
                        registerDTO.setPatientName(e.get("PATIENTNAME")==null?null:(String)e.get("PATIENTNAME"));
                        registerDTO.setPno(e.get("PNO")==null?null:(String.valueOf(e.get("PNO"))));
                        registerDTO.setSex(e.get("SEX")==null?null:(String)e.get("SEX"));
                        registerDTO.setVisitTime(e.get("VISITTIME")==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)e.get("VISITTIME")));
                        result.add(registerDTO);
                    }
                });
            });
        }
        return result;
    }

    @Override
    public Object patientInfo(String cardNo) {
        return null;
    }

    @Override
    public void NoticeData(String startTime, String endTime) {

    }

    @Override
    public List<HisRegisterYygh> hisRegisterYyghInfo(String startTime, String endTime) {
      return null;
    }
}
