package com.alpha.service;

import com.alpha.commons.core.pojo.inspcetion.FSRequest;
import com.alpha.commons.core.pojo.inspcetion.FSResult;
import com.alpha.commons.core.pojo.inspcetion.JYRequest;
import com.alpha.commons.core.pojo.inspcetion.JYResult;
import com.alpha.pojo.vo.InspectionDetailVO;

import java.util.List;

/**
 * Created by edz on 2018/10/31.
 */
public interface FSJobService {
    List<FSRequest> fs_request(String startTime, String endTime);
    List<FSResult> fs_result(String startTime, String endTime);
    List<FSResult> fs_resultByReportId(String reportId);
    void push();
}
