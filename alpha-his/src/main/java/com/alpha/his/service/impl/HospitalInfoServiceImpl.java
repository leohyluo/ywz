package com.alpha.his.service.impl;

import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.exception.ServiceException;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.his.mapper.HospitalDeptMapper;
import com.alpha.his.mapper.HospitalInfoMapper;
import com.alpha.his.service.HospitalInfoService;
import com.alpha.server.rpc.diagnosis.pojo.HospitalDept;
import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HospitalInfoServiceImpl implements HospitalInfoService {

    @Resource
    private HospitalDeptMapper hospitalDeptMapper;

    @Resource
    private HospitalInfoMapper hospitalInfoMapper;

    @Resource
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    @Value("${hospital.code}")
    private String defaultHospitalCode;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public HospitalInfo getByHospitalCode(String hospitalCode) {
        HospitalInfo param = new HospitalInfo();
        param.setHospitalCode(hospitalCode);
        List<HospitalInfo> hospitalInfoList = hospitalInfoMapper.select(param);
        HospitalInfo hospitalInfo = null;
        if (CollectionUtils.isNotEmpty(hospitalInfoList)) {
            hospitalInfo = hospitalInfoList.get(0);
        }
        return hospitalInfo;
    }

    @Override
    public void deptValidate(String appType, String pno) {
        HospitalDept hospitalDeptParam = new HospitalDept();
        hospitalDeptParam.setHospitalCode(defaultHospitalCode);
        List<HospitalDept> hospitalDeptList = hospitalDeptMapper.select(hospitalDeptParam);
        //患者挂号信息
        HisRegisterYygh hisRegisterYyghParam = new HisRegisterYygh();
        hisRegisterYyghParam.setPno(pno);
        List<HisRegisterYygh> hisRegisterYyghList = hisRegisterYyghMapper.select(hisRegisterYyghParam);
        if (CollectionUtils.isEmpty(hisRegisterYyghList)) {
            hisRegisterYyghParam = new HisRegisterYygh();
            hisRegisterYyghParam.setYno(pno);
            hisRegisterYyghList = hisRegisterYyghMapper.select(hisRegisterYyghParam);
        }
        if (CollectionUtils.isEmpty(hisRegisterYyghList)) {
            logger.error("根据{}找不到挂号记录", pno);
            throw new ServiceException(ResponseStatus.INVALID_VALUE);
        }
        //患者挂号对应的科室
        HisRegisterYygh hisRegisterYygh = hisRegisterYyghList.get(0);
        String deptName = hisRegisterYygh.getDeptName();
        if (StringUtils.isEmpty(deptName)) {
            logger.error("预约记录{}没有挂号科室", pno);
            throw new ServiceException(ResponseStatus.DEPT_IS_NULL);
        }
        HospitalDept dept = null;
        Map<String, HospitalDept> deptMap = hospitalDeptList.stream().collect(Collectors.toMap(HospitalDept::getDeptName, Function.identity()));
        for (String itemDeptName : deptMap.keySet()) {
            if (deptName.contains(itemDeptName)) {
                dept = deptMap.get(itemDeptName);
                break;
            }
        }
        if (dept == null) {
            String errorMsg = deptName + "暂未开放";
            throw new ServiceException(ResponseStatus.DEPT_NOT_OPEN, errorMsg);
        }
        //年龄较验
        if (dept.getMinAge() != null && dept.getMaxAge() != null) {
            String birthStr = hisRegisterYygh.getBirthday();
            float age = 0;
            try {
                Date birth = DateUtils.string2Date(birthStr);
                age = DateUtils.getAge(birth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (age < dept.getMinAge() || age > dept.getMaxAge()) {
                String errorMsg = deptName + "只对"+dept.getMinAge()+"岁到"+dept.getMaxAge()+"岁的人群开放";
                throw new ServiceException(ResponseStatus.AGE_NOT_MATCH, errorMsg);
            }
        }
        //性别较验
        if (dept.getGender() != null) {
            String sex = hisRegisterYygh.getSex();
            Integer gender = sex.contains("女") ? 1 : 2;
            if (dept.getGender() != gender) {
                String errorMsg = deptName + "有性别限制";
                throw new ServiceException(ResponseStatus.GENDER_NOT_MATCH, errorMsg);
            }
        }
    }
}
