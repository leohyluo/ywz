package com.alpha.self.diagnosis.configuration;

import com.alpha.self.diagnosis.ws.WSHisService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

@Configuration
public class CXFConfiguration {

    @Resource
    private Bus bus;

    @Resource
    private WSHisService hisService;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, hisService);
        endpoint.publish("/hisService");
        return endpoint;
    }
}
