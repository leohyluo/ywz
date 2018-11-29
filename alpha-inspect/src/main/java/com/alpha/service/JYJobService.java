package com.alpha.service;

import com.alpha.commons.core.pojo.inspcetion.JYRequest;
import com.alpha.commons.core.pojo.inspcetion.JYResult;
import com.alpha.pojo.vo.InspectionDetailVO;

import java.util.List;

/**
 * Created by edz on 2018/10/29.
 */
public interface JYJobService {

    List<JYRequest> jy_request(String startTime,String endTime);
    List<JYResult> jy_result(String startTime, String endTime);
    List<JYResult> jy_resultByReportId(String reportId);
    void push();
    List<InspectionDetailVO> detailByReportId(String reportNo);

}

