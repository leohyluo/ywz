package com.alpha.service;


import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.core.his.OutPatientService;

/**
 * Created by edz on 2018/10/19.
 */
public class QuartzDataServiceImlFactory  {

    public static OutPatientService createService(String hospitalCode){
        switch (hospitalCode){
            case "A001":
                return SpringContextHolder.getBean(ETYYJOBImpl.class);
            case "A002":
                return SpringContextHolder.getBean(GMRMYYJOBImpl.class);
            case "A003":
                return SpringContextHolder.getBean(YIFUYYJOBImpl.class);
            default://默认儿童医院
                return SpringContextHolder.getBean(ETYYJOBImpl.class);
        }
    }

}
