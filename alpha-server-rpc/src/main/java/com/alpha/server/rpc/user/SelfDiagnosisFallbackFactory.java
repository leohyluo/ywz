package com.alpha.server.rpc.user;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SelfDiagnosisFallbackFactory implements FallbackFactory<SelfDiagnosisFeign> {

    @Override
    public SelfDiagnosisFeign create(Throwable e) {
        return new SelfDiagnosisFeign() {
            @Override
            public String getUserInfo() {
                // TODO Auto-generated method stub
                return "fallback cause of ";
            }
        };
    }

}
