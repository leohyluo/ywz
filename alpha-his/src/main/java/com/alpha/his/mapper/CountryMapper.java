package com.alpha.his.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.his.pojo.dao.Country;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * Created by Administrator on 2018/10/21.
 */
@Mapper
public interface CountryMapper extends MyMapper<Country> {
    List<Country> selectKeyword(String keyword);
    void updatebycode(String code);
    String selectByCode(String code);
}
