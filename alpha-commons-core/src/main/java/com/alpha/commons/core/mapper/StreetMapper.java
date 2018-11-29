package com.alpha.commons.core.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.commons.core.pojo.Street;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by HP on 2018/7/11.
 */
@Mapper
public interface StreetMapper extends MyMapper<Street> {
    List<String> getStreetByName(@Param("address") String address);
}
