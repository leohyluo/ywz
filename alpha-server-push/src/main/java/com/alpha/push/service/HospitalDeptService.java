package com.alpha.push.service;

import com.alpha.push.domain.HospitalDept;

public interface HospitalDeptService {

    HospitalDept getByHospitalCodeAndDeptName(String hospitalCode, String deptName);
}
