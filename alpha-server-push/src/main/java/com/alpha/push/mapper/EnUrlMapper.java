package com.alpha.push.mapper;


import com.alpha.push.domain.EnUrlDTO;

import java.util.List;

/**
 * Created by MR.Wu on 2018-06-27.
 */
public interface EnUrlMapper {

    void addData(EnUrlDTO enUrlDTO);

    EnUrlDTO getData(String enUrl);

    void batchAddData(List<EnUrlDTO> list);
}
