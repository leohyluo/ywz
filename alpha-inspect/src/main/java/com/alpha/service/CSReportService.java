package com.alpha.service;

import com.alpha.commons.core.pojo.inspcetion.CSReport;

import java.util.List;

/**
 * Created by edz on 2018/10/31.
 */
public interface CSReportService {
    List<CSReport> cs_request(String startTime, String endTime);
    void push();
}
