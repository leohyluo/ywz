package com.alpha.his.service.etyy;

import com.alpha.commons.core.pojo.OutPatientInfo;

import java.util.List;

/**
 * Created by HP on 2018/6/4.
 */
public interface OutptientInfroService {
    List<OutPatientInfo> getOutpatientInfoByMZHM(String patientName, String patientId, String mzhm, String cardNo);
}
