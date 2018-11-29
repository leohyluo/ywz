package com.alpha.his.webService;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * Created by Administrator on 2018/3/14.
 */
@Configuration
public class CxfConfig {


    @Autowired
    private Bus bus;
    @Autowired
    private UserCardService userCardService;
    @Autowired
    private HisPatientWeightService hisPatientWeightService;
    @Autowired
    private HisPatientInfoService hisPatientInfoService;

    @Autowired CommonsEMRService commonsEMRService;


    @Bean
    public Endpoint endpointUserCardService() {
        EndpointImpl endpoint = new EndpointImpl(bus,userCardService);
        endpoint.publish("/UserCardService");
        return endpoint;
    }

    @Bean
    public Endpoint endpointHisPatientWeightService() {
        EndpointImpl endpoint = new EndpointImpl(bus,hisPatientWeightService);
        endpoint.publish("/HisPatientWeightService");
        return endpoint;
    }

    @Bean
    public Endpoint endpointHisPatientInfoService() {
        EndpointImpl endpoint = new EndpointImpl(bus,hisPatientInfoService);
        endpoint.publish("/HisPatientInfoService");
        return endpoint;
    }

    @Bean
    public Endpoint endpointCommonsEMRService() {
        EndpointImpl endpoint = new EndpointImpl(bus,commonsEMRService);
        endpoint.publish("/CommonsEMRService");
        return endpoint;
    }

}
