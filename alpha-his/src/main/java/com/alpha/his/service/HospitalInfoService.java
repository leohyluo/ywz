package com.alpha.his.service;

import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;

public interface HospitalInfoService {

    HospitalInfo getByHospitalCode(String domain);

    void deptValidate(String appType, String pno);
}
