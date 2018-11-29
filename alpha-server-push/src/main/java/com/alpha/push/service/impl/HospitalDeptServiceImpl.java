package com.alpha.push.service.impl;

import com.alpha.push.domain.HospitalDept;
import com.alpha.push.mapper.HospitalDeptMapper;
import com.alpha.push.service.HospitalDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HospitalDeptServiceImpl implements HospitalDeptService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private HospitalDeptMapper hospitalDeptMapper;

    @Override
    public HospitalDept getByHospitalCodeAndDeptName(String hospitalCode, String deptName) {
        HospitalDept param = new HospitalDept();
        param.setHospitalCode(hospitalCode);

        List<HospitalDept> hospitalDeptList = hospitalDeptMapper.select(param);
        HospitalDept dept = null;
        for (HospitalDept itemHospitalDept : hospitalDeptList) {
            String itemDeptName = itemHospitalDept.getDeptName();
            if (deptName.contains(itemDeptName)) {
                dept = itemHospitalDept;
                break;
            }
        }
        return dept;
    }
}
