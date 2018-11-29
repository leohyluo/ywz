package com.alpha.his.service.HisServiceFactory;

import com.alpha.commons.core.his.OutPatientService;
import com.alpha.his.service.etyy.impl.ETYYHisRegisterImpl;
import com.alpha.his.service.gmyy.GMHisRegisterImpl;
import com.alpha.his.service.yfyy.NJYFYYHisRegisterServiceImpl;

/**
 * Created by HP on 2018/10/8.
 */
public class OutPatientServiceFactory {

    public static OutPatientService createService(String hospitalCode){
        switch (hospitalCode){
            case "A001":
                return new ETYYHisRegisterImpl();
            case "A002":
                return new GMHisRegisterImpl();
            case "A003":
                return new NJYFYYHisRegisterServiceImpl();
            default:
                return new ETYYHisRegisterImpl();
        }
    }
}
