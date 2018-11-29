package com.alpha.service;


import com.alpha.commons.core.pojo.inspcetion.XNDRequest;
import com.alpha.commons.core.pojo.inspcetion.XNDResult;
import com.alpha.pojo.vo.InspectionDetailVO;

import java.util.List;

/**
 * Created by edz on 2018/10/30.
 */
public interface XNDJobService {
    List<XNDRequest> xnd_request(String startTime, String endTime);
    List<XNDResult> xnd_result(String startTime, String endTime);
    List<XNDResult> xnd_resultByReportId(String reportId);
    void push();
}
