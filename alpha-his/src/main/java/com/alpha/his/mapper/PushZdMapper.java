package com.alpha.his.mapper;

import com.alpha.his.pojo.dto.PushinfoZd;

import java.util.List;

/**
 * Created by MR.Wu on 2018-06-27.
 */
public interface PushZdMapper {

    List<PushinfoZd> getInfos();
    List<String> pushNoticePhone();

}
