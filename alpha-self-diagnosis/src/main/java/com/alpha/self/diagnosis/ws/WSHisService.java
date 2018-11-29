package com.alpha.self.diagnosis.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "ws.diagnosis.self.alpha.com")
public interface WSHisService {

    @WebMethod
    String sayHello(@WebParam(name = "userName") String name);
}
