package com.alpha.self.diagnosis.ws;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(serviceName = "HisApiService", targetNamespace = "ws.diagnosis.self.alpha.com", endpointInterface = "com.alpha.self.diagnosis.ws.WSHisService")
@Component
public class WsHisServiceImpl implements WSHisService {

    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
