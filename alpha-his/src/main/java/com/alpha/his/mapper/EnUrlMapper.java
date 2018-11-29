package com.alpha.his.mapper;

import com.alpha.his.pojo.dto.EnUrlDTO;

/**
 * Created by MR.Wu on 2018-06-27.
 */
public interface EnUrlMapper {

    void addData(EnUrlDTO enUrlDTO);

    EnUrlDTO getData(String enUrl);
}
