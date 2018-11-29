package com.alpha.server.rpc.his;

import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HospitalFallbackFactory implements FallbackFactory<HospitalFeign> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public HospitalFeign create(Throwable throwable) {
        return new HospitalFeign() {
            @Override
            public HospitalInfo getHospitalInfo(String hospitalCode) {
                logger.error("获取医院信息失败,fallback");
                return null;
            }

            @Override
            public ResponseMessage saveDiagnosisResult(String allParam) {
                logger.error("保存医生病历失败,fallback");
                return null;
            }
        };
    }
}
