package com.alpha.push.mapper;


import com.alpha.push.domain.PushinfoZd;

import java.util.List;

/**
 * Created by MR.Wu on 2018-06-27.
 */
public interface PushZdMapper {

    List<PushinfoZd> getInfos();

    List<String> pushNoticePhone();
}
