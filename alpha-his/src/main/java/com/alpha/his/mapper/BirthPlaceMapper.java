package com.alpha.his.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.his.pojo.dao.BirthPlace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * Created by Administrator on 2018/10/21.
 */
@Mapper
public interface BirthPlaceMapper extends MyMapper<BirthPlace> {
    List<BirthPlace> selectKeyword(String keyword);
    void updatebycode(String code);
    String selectByCode(String code);
}
