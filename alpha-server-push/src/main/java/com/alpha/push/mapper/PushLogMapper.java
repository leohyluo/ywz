package com.alpha.push.mapper;


import com.alpha.push.domain.PushInfoLog;

import java.util.List;

/**
 * Created by MR.Wu on 2018-06-27.
 */
public interface PushLogMapper {

    void recoder(PushInfoLog pushInfoLog);

    int existRecoder(String hashCode);

    void batch(List<PushInfoLog> pushInfoLogs);
}
