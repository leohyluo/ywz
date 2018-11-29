package com.alpha.server.rpc.his;

import com.alpha.commons.web.ResponseMessage;
import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "alpha-his", fallbackFactory = HospitalFallbackFactory.class)
public interface HospitalFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/hospital/get")
    public HospitalInfo getHospitalInfo(@RequestParam("hospitalCode") String hospitalCode);

    @RequestMapping(method = RequestMethod.POST, value = "/hospital/diagnosisResult/save")
    public ResponseMessage saveDiagnosisResult(@RequestParam("allParam") String allParam);
}
